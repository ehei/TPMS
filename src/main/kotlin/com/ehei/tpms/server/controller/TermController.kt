package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["api/terms"])
@CrossOrigin("*")
class TermController(
    @Autowired val termRepository: TermRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun getTerm(@PathVariable(name = "id") id: Long): ResponseEntity<Term> {

        try {
            val findById: Optional<Term> = termRepository.findById(id)

            if (findById.isEmpty) {
                return ResponseEntity.notFound().build()
            }

            return ResponseEntity.ok().body(findById.get())
        } catch (noSuchElement: NoSuchElementException) {
            return ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun getTerms(): ResponseEntity<List<Term>> {

        val findAll = termRepository.findAll()

        return ResponseEntity.ok()
            .header("X-Total-Count", "" + findAll.size)
            .header("Access-Control-Expose-Headers", "X-Total-Count")
            .body(findAll)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun create(@RequestBody term: Term): ResponseEntity<Term> {

        val savedTerm = termRepository.save(term)

        return ResponseEntity.ok(savedTerm)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody termToUpdate: Term): ResponseEntity<Term> {

        val termInRepo = termRepository.findById(id).get()

        val updated = Term(
            id = id,
            title = when (termToUpdate.title == null) {
                true -> termInRepo.title
                else -> termToUpdate.title
            },
            startDate = when(termToUpdate.startDate == null) {
                true -> termInRepo.startDate
                else -> termToUpdate.startDate
            },
            endDate = when(termToUpdate.endDate == null) {
                true -> termInRepo.endDate
                else -> termToUpdate.endDate
            },
            course_ids = termToUpdate.course_ids
        )

        val savedTerm = termRepository.save(updated)

        return ResponseEntity.ok(savedTerm)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    fun delete(@PathVariable(name = "id") id: Long) {

        termRepository.deleteById(id)
    }
}
