package com.ehei.tpms.server.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TermTest {

    @Test
    internal fun `Add updates course`() {

        val course = Course("course name", CourseStatus.PlanToTake)
        val term = Term("Feb 1, 2022", "Jul 2, 2022", "term 1")

        term.add(course)

        assertThat(term.courses).contains(course)
        assertThat(course.term).isEqualTo(term)
    }

    @Test
    internal fun `Remove updates course`() {

        val course = Course("course name", CourseStatus.PlanToTake)
        val term = Term("Feb 1, 2022", "Jul 2, 2022", "term 1")

        term.add(course)
        term.remove(course)

        assertThat(term.courses).isEmpty()
        assertThat(course.term).isNull()
    }

    @Test
    internal fun `If course to remove is not in the term, do nothing`() {
        val course = Course("course name", CourseStatus.PlanToTake)
        val term = Term("Feb 1, 2022", "Jul 2, 2022", "term 1")
        val term2 = Term("Feb 1, 2022", "Jul 2, 2022", "term 2")

        course.term = term2

        term.remove(course)

        assertThat(course.term).isEqualTo(term2)
    }

    @Test
    internal fun `If course to add is already in the term, do nothing`() {
        val course = Course("course name", CourseStatus.PlanToTake)
        val term = Term("Feb 1, 2022", "Jul 2, 2022", "term 1")

        term.add(course)
        term.add(course)

        assertThat(course.term).isEqualTo(term)
    }

    @Test
    internal fun `If course belongs to another term, do nothing`() {
        val course = Course("course name", CourseStatus.PlanToTake)
        val term = Term("Feb 1, 2022", "Jul 2, 2022", "term 1")
        val term2 = Term("Feb 1, 2022", "Jul 2, 2022", "term 2")

        term2.add(course)
        term.add(course)

        assertThat(course.term).isEqualTo(term2)
    }
}