package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.InstructorRepository
import com.ehei.tpms.server.model.Instructor
import com.ehei.tpms.server.model.Term
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["api/instructors"])
@CrossOrigin("*")
class InstructorController(
    @Autowired val instructorRepository: InstructorRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getInstructor(@PathVariable(name = "id") id: Long): ResponseEntity<Instructor> {

        try {
            val findById: Optional<Instructor> = instructorRepository.findById(id)

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
    fun getTerms(): ResponseEntity<List<Instructor>> {

        val findAll = instructorRepository.findAll()

        return ResponseEntity.ok()
            .header("X-Total-Count", "" + findAll.size)
            .header("Access-Control-Expose-Headers", "X-Total-Count")
            .body(findAll)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody instructor: Instructor): ResponseEntity<Instructor> {

        val savedInstructor = instructorRepository.save(instructor)

        return ResponseEntity.ok(savedInstructor)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody instructorToUpdate: Instructor): ResponseEntity<Instructor> {

        val instructorInRepo = instructorRepository.findById(id).get()

        val updated = Instructor(
            id = id,
            name = when (instructorToUpdate.name == null) {
                true -> instructorInRepo.name
                else -> instructorToUpdate.name
            },
            phoneNumber = when(instructorToUpdate.phoneNumber == null) {
                true -> instructorInRepo.phoneNumber
                else -> instructorToUpdate.phoneNumber
            },
            emailAddress = when(instructorToUpdate.emailAddress == null) {
                true -> instructorInRepo.emailAddress
                else -> instructorToUpdate.emailAddress
            }
        )

        val savedInstructor = instructorRepository.save(updated)

        return ResponseEntity.ok(savedInstructor)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) {

        instructorRepository.deleteById(id)
    }
}