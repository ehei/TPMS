package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.*
import com.ehei.tpms.server.model.*
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * FullTerms controller
 *
 * Return fully-formed JSON objects with all related data
 */
@Controller
@RequestMapping(path = ["api/fullTerms"])
@CrossOrigin("*")
class FullTermsController(
    val termRepository: TermRepository,
    val courseRepository: CourseRepository,
    val instructorRepository: InstructorRepository,
    val assessmentRepository: AssessmentRepository,
    val userRepository: UserRepository
) {
    /**
     * Get
     *
     * @param token
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(OK)
    fun get(@RequestHeader("Authorization") token: String, @PathVariable(name = "id") id: Long): ResponseEntity<FullTerm> {

        val found = termRepository.findById(id)

        if (found.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val foundCourses = courseRepository.findAll()
        val foundInstructors = instructorRepository.findAll()
        val foundAssessments = assessmentRepository.findAll()

        val fullTerm = toFullTerm(found.get(), foundCourses, foundInstructors, foundAssessments)

        return ResponseEntity.ok(fullTerm)
    }

    /**
     * Get all
     *
     * @param token
     * @return
     */
    @GetMapping
    @ResponseBody
    @ResponseStatus(OK)
    fun getAll(@RequestHeader("Authorization") token: String): ResponseEntity<List<FullTerm>> {

        val foundCourses = courseRepository.findAll()
        val foundInstructors = instructorRepository.findAll()
        val foundAssessments = assessmentRepository.findAll()

        val fullTerms =
            termRepository.findAll().map { term ->
                toFullTerm(term, foundCourses, foundInstructors, foundAssessments)
            }

        return createListResponse(fullTerms)
    }

    private fun toFullTerm(
        term: Term,
        foundCourses: MutableList<Course>,
        foundInstructors: MutableList<Instructor>,
        foundAssessments: MutableList<Assessment>
    ): FullTerm {
        val fullTerm = FullTerm(term.id, term.title, term.startDate, term.endDate)

        val foundUser = userRepository.findById(term.userId!!)

        if (foundUser.isPresent)
            fullTerm.user = foundUser.get()
        else
            fullTerm.user = UNKNOWN_USER

        if (foundCourses.isNotEmpty()) {
            val filteredCourses = foundCourses.filter { it.id in term.course_ids }
            val map: List<FullCourse> = filteredCourses.map { toFullCourse(it, foundInstructors, foundAssessments) }
            fullTerm.courses.addAll(map)
        }

        return fullTerm
    }

}

fun toFullCourse(course: Course, foundInstructors: MutableList<Instructor>, foundAssessments: MutableList<Assessment>): FullCourse
{
    val fullCourse = FullCourse.fromCourse(course)

    if (foundInstructors.isNotEmpty()) {
        foundInstructors.filter { it.id in course.instructor_ids }.forEach {
            fullCourse.instructors.add(it)
        }
    }

    if (foundAssessments.isNotEmpty()) {
        foundAssessments.filter { it.id in course.assessment_ids }.forEach {
            fullCourse.assessments.add(it)
        }
    }

    return fullCourse
}