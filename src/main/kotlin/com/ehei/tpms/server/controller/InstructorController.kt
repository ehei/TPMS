package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.InstructorRepository
import com.ehei.tpms.server.model.Instructor
import com.ehei.tpms.server.model.UNKNOWN_USER
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
    @Autowired val repository: InstructorRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun get(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<Instructor> {

        try {
            val findById: Optional<Instructor> = repository.findById(id)

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
    fun getAll(@RequestHeader("Authorization") token: String): ResponseEntity<List<Instructor>> {

        var found = listOf<Instructor>()
        val user = getValidToken(token)
        if (user != UNKNOWN_USER)
            found = repository.findAll().filter { record -> record.userId == user.id }

        return createListResponse(found)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestHeader("Authorization") token: String, @RequestBody instructor: Instructor): ResponseEntity<Instructor> {

        val user = getValidToken(token)
        if (user == UNKNOWN_USER)
            return ResponseEntity.badRequest().build()

        instructor.userId = user.id
        val savedInstructor = repository.save(instructor)

        return ResponseEntity.ok(savedInstructor)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long, @RequestBody instructorToUpdate: Instructor): ResponseEntity<Instructor> {

        val found = repository.findById(id)
        val user = getValidToken(token)
        if (found.isEmpty || user == UNKNOWN_USER || user.id != found.get().userId)
            return ResponseEntity.notFound().build()

        val instructor = found.get()

        val updated = Instructor(
            id = id,
            name = when (instructorToUpdate.name == null) {
                true -> instructor.name
                else -> instructorToUpdate.name
            },
            phoneNumber = when(instructorToUpdate.phoneNumber == null) {
                true -> instructor.phoneNumber
                else -> instructorToUpdate.phoneNumber
            },
            emailAddress = when(instructorToUpdate.emailAddress == null) {
                true -> instructor.emailAddress
                else -> instructorToUpdate.emailAddress
            },
            userId = user.id
        )

        val savedInstructor = repository.save(updated)

        return ResponseEntity.ok(savedInstructor)
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