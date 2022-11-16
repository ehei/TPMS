package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.UNKNOWN_USER
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
    fun getTerm(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<Term> {

        try {
            val findById = termRepository.findById(id)

            if (findById.isEmpty || ! isAuthorized(token, findById.get().userId)) {
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
    fun getTerms(@RequestHeader("Authorization") token: String): ResponseEntity<List<Term>> {

        var found = listOf<Term>()
        val user = getValidToken(token)
        if (user != UNKNOWN_USER)
            found = termRepository.findAll().filter { record -> record.userId == user.id }

        return createListResponse(found)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun create(@RequestHeader("Authorization") token: String, @RequestBody term: Term): ResponseEntity<Term> {

        val user = getValidToken(token)
        if (user == UNKNOWN_USER)
            return ResponseEntity.badRequest().build()

        term.userId = user.id
        val savedTerm = termRepository.save(term)

        return ResponseEntity.ok(savedTerm)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun update(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long, @RequestBody termToUpdate: Term): ResponseEntity<Term> {

        val termInRepo = termRepository.findById(id)

        val user = getValidToken(token)
        if (termInRepo.isEmpty || user == UNKNOWN_USER || user.id != termInRepo.get().userId)
            return ResponseEntity.notFound().build()

        val term = termInRepo.get()

        val updated = Term(
            id = id,
            title = when (termToUpdate.title == null) {
                true -> term.title
                else -> termToUpdate.title
            },
            startDate = when(termToUpdate.startDate == null) {
                true -> term.startDate
                else -> termToUpdate.startDate
            },
            endDate = when(termToUpdate.endDate == null) {
                true -> term.endDate
                else -> termToUpdate.endDate
            },
            course_ids = termToUpdate.course_ids
        )

        val savedTerm = termRepository.save(updated)

        return ResponseEntity.ok(savedTerm)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    fun delete(@RequestHeader("Authorization") token: String?, @PathVariable(name = "id") id: Long):
        ResponseEntity<Long> {

        val termInRepo = termRepository.findById(id)

        if (! isAuthorized(token, termInRepo.get().userId))
            return ResponseEntity.notFound().build()

        termRepository.deleteById(id)
        return ResponseEntity.ok(id)
    }
}
