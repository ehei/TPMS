package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.AssessmentRepository
import com.ehei.tpms.server.model.Assessment
import com.ehei.tpms.server.model.UNKNOWN_USER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["api/assessments"])
@CrossOrigin("*")
class AssessmentController(
    @Autowired val repository: AssessmentRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun get(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<Assessment> {

        try {
            val findById: Optional<Assessment> = repository.findById(id)

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
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@RequestHeader("Authorization") token: String): ResponseEntity<List<Assessment>> {

        var found = listOf<Assessment>()
        val user = getValidToken(token)
        if (user != UNKNOWN_USER)
            found = repository.findAll().filter { record -> record.userId == user.id }

        return createListResponse(found)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestHeader("Authorization") token: String, @RequestBody assessment: Assessment): ResponseEntity<Assessment> {

        val user = getValidToken(token)
        if (user == UNKNOWN_USER)
            return ResponseEntity.badRequest().build()

        assessment.userId = user.id
        val saved = repository.save(assessment)

        return ResponseEntity.ok(saved)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long, @RequestBody assessmentToUpdate: Assessment): ResponseEntity<Assessment> {

        val found = repository.findById(id)
        val user = getValidToken(token)
        if (found.isEmpty || user == UNKNOWN_USER || user.id != found.get().userId)
            return ResponseEntity.notFound().build()

        val assessmentInRepo = found.get()

        val updated = Assessment(
            id = id,
            title = when (assessmentToUpdate.title == null) {
                true -> assessmentInRepo.title
                else -> assessmentToUpdate.title
            },
            startDate = when(assessmentToUpdate.startDate == null) {
                true -> assessmentInRepo.startDate
                else -> assessmentToUpdate.startDate
            },
            endDate = when(assessmentToUpdate.endDate == null) {
                true -> assessmentInRepo.endDate
                else -> assessmentToUpdate.endDate
            },
            performance = assessmentToUpdate.performance,
            userId = user.id
        )

        val savedAssessment = repository.save(updated)

        return ResponseEntity.ok(savedAssessment)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long):
            ResponseEntity<Long> {

        val found = repository.findById(id)

        if (! isAuthorized(token, found.get().userId))
            return ResponseEntity.notFound().build()

        repository.deleteById(id)
        return ResponseEntity.ok(id)
    }
}