package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.CourseRepository
import com.ehei.tpms.server.model.Course
import com.ehei.tpms.server.model.UNKNOWN_USER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["api/courses"])
@CrossOrigin("*")
class CourseController(
    @Autowired val repository: CourseRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun get(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<Course> {

        try {
            val findById: Optional<Course> = repository.findById(id)

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
    fun getAll(@RequestHeader("Authorization") token: String): ResponseEntity<List<Course>> {

        var found = listOf<Course>()
        val user = getValidToken(token)
        if (user != UNKNOWN_USER)
            found = repository.findAll().filter { record -> record.userId == user.id }

        return createListResponse(found)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestHeader("Authorization") token: String, @RequestBody course: Course): ResponseEntity<Course> {

        val user = getValidToken(token)
        if (user == UNKNOWN_USER)
            return ResponseEntity.badRequest().build()

        course.userId = user.id
        val savedCourse = repository.save(course)

        return ResponseEntity.ok(savedCourse)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long, @RequestBody courseToUpdate: Course): ResponseEntity<Course> {

        val found = repository.findById(id)
        val user = getValidToken(token)
        if (found.isEmpty || user == UNKNOWN_USER || user.id != found.get().userId)
            return ResponseEntity.notFound().build()

        val courseInRepo = found.get()

        val updated = Course(
            id = id,
            title = when (courseToUpdate.title == null) {
                true -> courseInRepo.title
                else -> courseToUpdate.title
            },
            startDate = when(courseToUpdate.startDate == null) {
                true -> courseInRepo.startDate
                else -> courseToUpdate.startDate
            },
            endDate = when(courseToUpdate.endDate == null) {
                true -> courseInRepo.endDate
                else -> courseToUpdate.endDate
            },
            instructor_ids = courseToUpdate.instructor_ids,
            assessment_ids = courseToUpdate.assessment_ids,
            notes = courseToUpdate.notes,
            userId = user.id
        )

        val savedCourse = repository.save(updated)

        return ResponseEntity.ok(savedCourse)
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