package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.*
import com.ehei.tpms.server.util.convertToString
import com.ehei.tpms.server.util.createDateFrom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RepositoryIntegrationTests(
    @Autowired
    private val entityManager: TestEntityManager
) {
    val startDate = createDateFrom(2022, 5, 15)
    val endDate =  createDateFrom(2022, 7, 1)

    @Test
    fun `Data can be create and saved`() {

        val note1 = Note("some text")
        val note2 = Note("some more text")

        val course1 = Course("first course", CourseStatus.PlanToTake)
        val course2 = Course("second course", CourseStatus.Completed)

        course1.add(note1)
        course2.add(note2)

        course1.add(Instructor("instruct Or", "123-444-5555", "io@gmail.com"))

        course1.add(Assessment("assessment 1-1", false))

        course2.add(Assessment("assessment 1", false))
        course2.add(Assessment("assessment 2nd", true))

        val term = Term(startDate.convertToString(), endDate.convertToString(), "TheFirstTerm")

        term.add(course1)
        term.add(course2)

        entityManager.persist(note1)
        entityManager.persist(note2)

        course1.instructors.forEach{ entityManager.persist(it) }
        course1.assessments.forEach{ entityManager.persist(it) }

        entityManager.persist(course1)
        entityManager.persist(course2)

        val persistAndFlush = entityManager.persistAndFlush(term)

        assertThat(persistAndFlush).isNotNull

        val found = persistAndFlush!!

        assertThat(found.id).isGreaterThan(0)

        assertThat(found.courses).containsExactly(course1, course2)

        assertThat(course1.term).isSameAs(found)
        assertThat(course2.term).isSameAs(found)

        val foundTerm = entityManager.find(Term::class.java, term.id)
//        val foundCourses = courseRepository.findAll()
//        val foundAssessments = assessmentRepository.findAll()
//        val foundInstructors = instructorRepository.findAll()
//        val foundNotes = noteRepository.findAll()

        assertThat(foundTerm).isEqualTo(term)
//        assertThat(foundCourses).hasSize(2)
//        assertThat(foundAssessments).hasSize(3)
//        assertThat(foundInstructors).hasSize(1)
//        assertThat(foundNotes).hasSize(2)
    }

//    @Test
//    fun `Can create instructors and assessments and add them to courses later`() {
//
//        val instructor1 = Instructor("first", "", "")
//        val instructor2 = Instructor("2nd", "", "")
//        val instructor3 = Instructor("3", "", "")
//
//        val assessment11 = Assessment("assessment 1-1", false)
//        val assessment12 = Assessment("assessment 1-2", true)
//        val assessment21 = Assessment("assessment 2-1", false)
//
//        entityManager.persist(instructor1)
//        entityManager.persist(instructor2)
//        entityManager.persist(instructor3)
//
//        entityManager.persist(assessment11)
//        entityManager.persist(assessment12)
//        entityManager.persist(assessment21)
//
//        val course = Course("some Course", CourseStatus.PlanToTake)
//        val persistAndFlush = entityManager.persistAndFlush(course)
//
//        assertThat(persistAndFlush).isNotNull
//
//        val found = persistAndFlush!!
//
//        course.add(assessment11)
//        assessment11.course = course
//        course.add(assessment12)
//        assessment12.course = course
//
//        course.add(instructor1)
//        course.add(instructor2)
//
//        instructor1.add(course)
//        instructor2.add(course)
//
//        courseRepository.saveAndFlush(course)
//
//        assertThat(course.assessments).containsExactly(assessment11, assessment12)
//        assertThat(course.instructors).containsExactly(instructor1, instructor2)
//
//        assertThat(course.instructors[0].courses).containsExactly(course)
//
//        assertThat(instructor1.courses).containsExactly(course)
//        assertThat(instructor2.courses).containsExactly(course)
//    }
}