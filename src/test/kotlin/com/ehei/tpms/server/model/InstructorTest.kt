package com.ehei.tpms.server.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InstructorTest {
    @Test
    internal fun `Constructor sets fields and courses defaults to empty set`() {

        val instructor = Instructor("name", "phone", "email")

        assertThat(instructor.name).isEqualTo("name")
        assertThat(instructor.phoneNumber).isEqualTo("phone")
        assertThat(instructor.emailAddress).isEqualTo("email")
        assertThat(instructor.courses).isEmpty()
    }
}