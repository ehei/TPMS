package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.*
import com.ehei.tpms.server.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*


class FullTermsControllerTest{

    private val termRepository: TermRepository = mock()
    private val courseRepository: CourseRepository = mock()
    private val instructorRepository: InstructorRepository = mock()
    private val assessmentRepository: AssessmentRepository = mock()
    private val userRepository: UserRepository = mock()

    private lateinit var fullTermsController: FullTermsController

    private val user = User(1, "a", "b", "c")
    private val user2 = User(2, "a", "b", "c")
    private val user3 = User(3, "a", "b", "c")

    private val instructor = Instructor(id = 1, name = "n1", phoneNumber = "1", emailAddress = "1")
    private val instructor2 = Instructor(id = 2, name = "n2", phoneNumber = "2", emailAddress = "2")

    private val assessment = Assessment(id = 1, title = "a1", startDate = "02/03/2022", endDate = "04/04/2022", performance = false)
    private val assessment2 = Assessment(id = 2, title = "a2", startDate = "03/01/2022", endDate = "05/02/2022", performance = true)

    private val course = Course(id = 1, title = "1", status = CourseStatus.PlanToTake, startDate = "01/01/2022", endDate = "03/04/2022")
    private val course2 = Course(id = 2, title = "2", status = CourseStatus.Completed, startDate = "01/01/2022", endDate = "03/04/2022")
    private val course3 = Course(id = 3, title = "3", status = CourseStatus.Dropped, startDate = "01/01/2022", endDate = "03/04/2022")
    private val course4 = Course(id = 4, title = "4", status = CourseStatus.InProgress, startDate = "01/01/2022", endDate = "03/04/2022")

    private lateinit var userAsString: String
    private lateinit var userAsString2: String
    private lateinit var userAsString3: String

    private val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
    private val term2 = Term(id = 2, title = "2", startDate = "2022/12/31", endDate = "2023/01/30", userId = user2.id)
    private val term3 = Term(id = 3, title = "3", startDate = "2022/12/31", endDate = "2023/01/30", userId = user3.id)

    @BeforeEach
    fun setUp() {

        fullTermsController = FullTermsController(
            termRepository,
            courseRepository,
            instructorRepository,
            assessmentRepository,
            userRepository
        )

        userAsString = ObjectMapper().writeValueAsString(user)
        userAsString2 = ObjectMapper().writeValueAsString(user2)
        userAsString3 = ObjectMapper().writeValueAsString(user3)

        term.course_ids.add(course.id!!)
        term.course_ids.add(course2.id!!)
        term3.course_ids.add(course3.id!!)

        course.instructor_ids.add(instructor.id!!)
        course.instructor_ids.add(instructor2.id!!)
        course.assessment_ids.add(assessment.id!!)

        course2.instructor_ids.add(instructor2.id!!)

        course3.instructor_ids.add(instructor.id!!)
        course3.assessment_ids.add(assessment2.id!!)

        whenever(termRepository.findAll()).thenReturn(listOf(term, term2, term3))
        whenever(userRepository.findAll()).thenReturn(listOf(user, user2, user3))
        whenever(assessmentRepository.findAll()).thenReturn(listOf(assessment, assessment2))
        whenever(instructorRepository.findAll()).thenReturn(listOf(instructor, instructor2))
        whenever(courseRepository.findAll()).thenReturn(listOf(course, course2, course3, course4))

        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(userRepository.findById(2)).thenReturn(Optional.of(user2))
        whenever(userRepository.findById(3)).thenReturn(Optional.of(user3))
    }

    @Test
    fun `returns all terms from all users as JSON`() {

        val fullCourse = FullCourse.fromCourse(course)
        fullCourse.instructors.add(instructor)
        fullCourse.instructors.add(instructor2)
        fullCourse.assessments.add(assessment)

        val fullCourse2 = FullCourse.fromCourse(course2)
        fullCourse2.instructors.add(instructor2)

        val fullCourse3 = FullCourse.fromCourse(course3)
        fullCourse3.instructors.add(instructor)
        fullCourse3.assessments.add(assessment2)

        val fullTerm = FullTerm(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", user = user)
        fullTerm.courses.add(fullCourse)
        fullTerm.courses.add(fullCourse2)
        val fullTerm2 = FullTerm(id = 2, title = "2", startDate = "2022/12/31", endDate = "2023/01/30", user = user2)
        val fullTerm3 = FullTerm(id = 3, title = "3", startDate = "2022/12/31", endDate = "2023/01/30", user = user3)
        fullTerm3.courses.add(fullCourse3)

        val foundTerms = fullTermsController.getAll(userAsString).body

        assertThat(foundTerms).contains(fullTerm)
        assertThat(foundTerms).contains(fullTerm2)
        assertThat(foundTerms).contains(fullTerm3)
    }

    @Test
    fun `toFullTerm defaults to UnknownUser if user with id is not found`() {

        term.userId = 9129;

        val fullTerm = fullTermsController.toFullTerm(term, mutableListOf(), mutableListOf(), mutableListOf())

        assertThat(fullTerm.user).isSameAs(UNKNOWN_USER)
    }

    @Test
    fun `toFullTerm if course list is empty, then fullTerm course list will be empty`() {

        val fullTerm = fullTermsController.toFullTerm(term, mutableListOf(), mutableListOf(), mutableListOf())

        assertThat(fullTerm.courses).isEmpty()
    }

    @Test
    fun `toFullTerm if course list is not empty but no ids match, then fullTerm course list will be empty`() {

        term.course_ids.clear()
        term.course_ids.add(44)
        term.course_ids.add(88)
        term.course_ids.add(122)
        val fullTerm = fullTermsController.toFullTerm(
            term,
            mutableListOf(course, course2, course3),
            mutableListOf(instructor, instructor2),
            mutableListOf(assessment, assessment2))

        assertThat(fullTerm.courses).isEmpty()
    }

    @Test
    fun `toFullCourse if instructors or assessments are empty, course lists will remain empty`() {

        val fullCourse = fullTermsController.toFullCourse(course, mutableListOf(), mutableListOf())

        assertThat(fullCourse.instructors).isEmpty()
        assertThat(fullCourse.assessments).isEmpty()
    }

    @Test
    fun `toFullCourse if instructors and assessments are not empty but ids do not match, course lists will remain empty`() {

        course.assessment_ids.clear()
        course.assessment_ids.add(9481)
        course.instructor_ids.clear()
        course.instructor_ids.add(123)
        course.instructor_ids.add(423)

        val fullCourse = fullTermsController.toFullCourse(course, mutableListOf(), mutableListOf())

        assertThat(fullCourse.instructors).isEmpty()
        assertThat(fullCourse.assessments).isEmpty()
    }
}