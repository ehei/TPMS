package com.ehei.tpms.server.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CourseTest {

    @Test
    fun `Setting children sets Course as the parent`() {
        val course = Course("title of course", CourseStatus.PlanToTake)

        val note1 = Note("note 1")
        course.add(note1)
        val note2 = Note("note 2")
        course.add(note2)

        val assessment1 = Assessment("assessment 1", false)
        course.add(assessment1)
        val assessment2 = Assessment("assessment 2", true)
        course.add(assessment2)

        val instructor = Instructor("instructor 1", "123123123", "123123@gmail.com")
        course.add(instructor)

        assertThat(course.title).isEqualTo("title of course")
        assertThat(course.status).isEqualTo(CourseStatus.PlanToTake)

        assertThat(note1.course).isSameAs(course)
        assertThat(note2.course).isSameAs(course)

        assertThat(course.notes).contains(note1, note2)

        assertThat(assessment1.course).isSameAs(course)
        assertThat(assessment2.course).isSameAs(course)

        assertThat(course.assessments).contains(assessment1, assessment2)

        assertThat(instructor.courses).contains(course)

        assertThat(course.instructors).contains(instructor)
    }

    @Test
    fun `Removing children removes Course as the parent`() {
        val course = Course("title of course", CourseStatus.PlanToTake)

        val note1 = Note("note 1")
        course.add(note1)
        val note2 = Note("note 2")
        course.add(note2)

        val assessment1 = Assessment("assessment 1", false)
        course.add(assessment1)
        val assessment2 = Assessment("assessment 2", true)
        course.add(assessment2)

        val instructor = Instructor("instructor 1", "123123123", "123123@gmail.com")
        course.add(instructor)

        course.remove(note1)
        course.remove(note2)
        course.remove(assessment1)
        course.remove(assessment2)
        course.remove(instructor)

        assertThat(note1.course).isNull()
        assertThat(note2.course).isNull()
        assertThat(course.notes).doesNotContain(note1, note2)

        assertThat(assessment1.course).isNull()
        assertThat(assessment2.course).isNull()
        assertThat(course.assessments).doesNotContain(assessment1, assessment2)

        assertThat(instructor.courses).doesNotContain(course)
        assertThat(course.instructors).doesNotContain(instructor)
    }

    @Test
    fun `Skip manipulating items that are not owned by this course`() {
        val course = Course("title of course", CourseStatus.PlanToTake)
        val course2 = Course("title of course 2", CourseStatus.Completed)

        val note1 = Note("note 1")
        course.add(note1)
        val note2 = Note("note 2")
        course2.add(note2)

        val assessment1 = Assessment("assessment 1", false)
        course2.add(assessment1)
        val assessment2 = Assessment("assessment 2", true)
        course.add(assessment2)

        val instructor = Instructor("instructor 1", "123123123", "123123@gmail.com")
        course2.add(instructor)

        course.remove(note1)
        course.remove(note2)
        course.remove(assessment1)
        course.remove(assessment2)
        course.remove(instructor)

        assertThat(note1.course).isNull()
        assertThat(note2.course).isSameAs(course2)
        assertThat(course.notes).doesNotContain(note1, note2)
        assertThat(course2.notes).contains(note2)

        assertThat(assessment1.course).isSameAs(course2)
        assertThat(assessment2.course).isNull()
        assertThat(course.assessments).doesNotContain(assessment1, assessment2)
        assertThat(course2.assessments).contains(assessment1)

        assertThat(instructor.courses).doesNotContain(course)
        assertThat(instructor.courses).contains(course2)
        assertThat(course.instructors).doesNotContain(instructor)
        assertThat(course2.instructors).contains(instructor)
    }

    @Test
    fun `Cannot add the same object twice`() {
        val course = Course("title of course", CourseStatus.PlanToTake)

        val note1 = Note("note 1")
        course.add(note1)
        course.add(note1)

        val assessment1 = Assessment("assessment 1", false)
        course.add(assessment1)
        course.add(assessment1)
        val assessment2 = Assessment("assessment 2", true)
        course.add(assessment2)
        course.add(assessment2)

        val instructor = Instructor("instructor 1", "123123123", "123123@gmail.com")
        course.add(instructor)
        course.add(instructor)

        assertThat(course.notes).hasSize(1)
        assertThat(course.assessments).hasSize(2)
        assertThat(course.instructors).hasSize(1)
    }

    @Test
    fun `instructors can be added to multiple courses at the same time`() {
        val course = Course("title of course", CourseStatus.PlanToTake)
        val course2 = Course("title of course 2", CourseStatus.Completed)

        val instructor1 = Instructor("instructor 1", "123123123", "123123@gmail.com")
        val instructor2 = Instructor("instructor 2", "123123123", "123123@gmail.com")

        course.add(instructor1)
        course.add(instructor2)

        course2.add(instructor2)
        course2.add(instructor1)

        assertThat(course.instructors).contains(instructor1, instructor2)
        assertThat(course2.instructors).contains(instructor1, instructor2)

        assertThat(instructor1.courses).contains(course, course2)
        assertThat(instructor2.courses).contains(course, course2)
    }

    @Test
    internal fun `Notes and Assessments can be swapped between courses`() {
        val course = Course("title of course", CourseStatus.PlanToTake)
        val course2 = Course("title of course 2", CourseStatus.Completed)

        val note1 = Note("note 1")
        course.add(note1)
        val note2 = Note("note 2")
        course2.add(note2)

        val assessment1 = Assessment("assessment 1", false)
        course.add(assessment1)
        val assessment2 = Assessment("assessment 2", true)
        course2.add(assessment2)

        course.add(note2)
        course.add(assessment2)

        assertThat(course.notes).contains(note1, note2)
        assertThat(course.assessments).contains(assessment1, assessment2)

        assertThat(course2.notes).isEmpty()
        assertThat(course2.assessments).isEmpty()

        assertThat(note1.course).isSameAs(course)
        assertThat(note2.course).isSameAs(course)
        assertThat(assessment1.course).isSameAs(course)
        assertThat(assessment2.course).isSameAs(course)
    }
}