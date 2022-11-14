package com.ehei.tpms.server.model

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TermTest {

    @Test
    fun `is serializable`() {

        val term = Term(12, "a title", "2022-08-01", "2022-10-01")

        val valueAsString = ObjectMapper().writeValueAsString(term)

        val termRead = ObjectMapper().readValue(valueAsString, Term::class.java)

        assertThat(termRead).isEqualTo(term)
    }

    @Test
    fun `equals and hashcode`() {

        val term = Term(13, "a title 1", "2022-08-01", "2022-10-01")
        val termEqual = Term(13, "a title 1", "2022-08-01", "2022-10-01")
        val termNotEqual1 = Term(13, "a title 2", "2022-08-01", "2022-10-01")
        val termNotEqual2 = Term(12, "a title 1", "2022-08-01", "2022-10-01")
        val termNotEqual3 = Term(13, "a title 1", "2022-09-01", "2022-10-01")
        val termNotEqual4 = Term(13, "a title 1", "2022-08-01", "2022-11-01")
        val termNotEqual5 = Term(13, "a title 1", "2022-08-01", "2022-10-01")
        termNotEqual5.course_ids.add(1)

        assertThat(term).isEqualTo(termEqual)
        assertThat(termEqual).isEqualTo(term)

        assertThat(term).isNotEqualTo(termNotEqual1)
        assertThat(term).isNotEqualTo(termNotEqual2)
        assertThat(term).isNotEqualTo(termNotEqual3)
        assertThat(term).isNotEqualTo(termNotEqual4)
        assertThat(term).isNotEqualTo(termNotEqual5)
    }
}