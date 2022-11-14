package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.CourseRepository
import com.ehei.tpms.server.model.Course
import com.ehei.tpms.server.model.Term
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
    @Autowired val courseRepository: CourseRepository
) {
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getCourse(@PathVariable(name = "id") id: Long): ResponseEntity<Course> {

        try {
            val findById: Optional<Course> = courseRepository.findById(id)

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
    fun getTerms(): ResponseEntity<List<Course>> {
        
        val findAll = courseRepository.findAll()

        return ResponseEntity.ok()
            .header("X-Total-Count", "" + findAll.size)
            .header("Access-Control-Expose-Headers", "X-Total-Count")
            .body(findAll)
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun create(@RequestBody course: Course): ResponseEntity<Course> {

        val savedCourse = courseRepository.save(course)

        return ResponseEntity.ok(savedCourse)
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable(name = "id") id: Long, @RequestBody courseToUpdate: Course): ResponseEntity<Course> {

        val courseInRepo = courseRepository.findById(id).get()

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
            notes = courseToUpdate.notes
        )

        val savedCourse = courseRepository.save(updated)

        return ResponseEntity.ok(savedCourse)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable(name = "id") id: Long) {

        courseRepository.deleteById(id)
    }
}