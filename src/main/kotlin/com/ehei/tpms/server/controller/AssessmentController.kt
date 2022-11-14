package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.AssessmentRepository
import com.ehei.tpms.server.model.Assessment
import com.ehei.tpms.server.model.Instructor
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
    @Autowired val assessmentRepository: AssessmentRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getAssessment(@PathVariable(name = "id") id: Long): ResponseEntity<Assessment> {

        try {
            val findById: Optional<Assessment> = assessmentRepository.findById(id)

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
    @ResponseStatus(HttpStatus.OK)
    fun getTerms(): ResponseEntity<List<Assessment>> {

        val findAll = assessmentRepository.findAll()

        return ResponseEntity.ok()
            .header("X-Total-Count", "" + findAll.size)
            .header("Access-Control-Expose-Headers", "X-Total-Count")
            .body(findAll)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody assessment: Assessment): ResponseEntity<Assessment> {

        val savedAssessment = assessmentRepository.save(assessment)

        return ResponseEntity.ok(savedAssessment)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody assessmentToUpdate: Assessment): ResponseEntity<Assessment> {

        val assessmentInRepo = assessmentRepository.findById(id).get()

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
            performance = assessmentToUpdate.performance
        )

        val savedAssessment = assessmentRepository.save(updated)

        return ResponseEntity.ok(savedAssessment)
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) {

        assessmentRepository.deleteById(id)
    }
}