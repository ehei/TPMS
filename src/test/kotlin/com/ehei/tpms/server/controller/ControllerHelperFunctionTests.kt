package com.ehei.tpms.server.controller

import com.ehei.tpms.server.model.UNKNOWN_USER
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ControllerHelperFunctionTests {

    private val user = User(1, "a", "b", "c")
    private lateinit var userAsString: String

    @BeforeEach
    fun setUp() {
        userAsString = ObjectMapper().writeValueAsString(user)
    }

    @Test
    fun `isAuthorized checks user id that comes in as the token versus the id in question`() {

        assertThat(isAuthorized(userAsString, user.id!!)).isTrue
    }

    @Test
    fun `isAuthorized returns false if there is no token`() {

        assertThat(isAuthorized(userAsString, 1321)).isFalse
    }

    @Test
    fun `isAuthorized returns false if there is no id`() {

        assertThat(isAuthorized(ObjectMapper().writeValueAsString(User()), 12)).isFalse
    }

    @Test
    fun `isAuthorized returns false if role is guest`() {

        assertThat(isAuthorized(ObjectMapper().writeValueAsString(User(role = "guest")), 12)).isFalse
    }

    @Test
    fun `isAuthorized returns false token is null`() {

        assertThat(isAuthorized(null, 12)).isFalse
    }

    @Test
    fun `isAuthorized returns false token is blank`() {

        assertThat(isAuthorized("", 12)).isFalse
    }

    @Test
    fun `if token is null or blank return UNKNOWN_USER`() {

        assertThat(getValidToken(null)).isEqualTo(UNKNOWN_USER)
        assertThat(getValidToken("")).isEqualTo(UNKNOWN_USER)
        assertThat(getValidToken("   ")).isEqualTo(UNKNOWN_USER)
    }

    @Test
    fun `if token is null or blank return -1`() {

        assertThat(userId(null)).isEqualTo(UNKNOWN_USER.id)
        assertThat(userId("")).isEqualTo(UNKNOWN_USER.id)
        assertThat(userId("   ")).isEqualTo(UNKNOWN_USER.id)
    }

    @Test
    fun `return id value of user`() {

        assertThat(userId(userAsString)).isEqualTo(user.id)
    }

    @Test
    fun `getValidToken returns UNKNOWN_USER if token is not well formed`() {

        assertThat(getValidToken(null)).isSameAs(UNKNOWN_USER)
        assertThat(getValidToken("")).isSameAs(UNKNOWN_USER)
        assertThat(getValidToken("     ")).isSameAs(UNKNOWN_USER)
        assertThat(getValidToken("""{ "whatever": "asdfasf" } """)).isSameAs(UNKNOWN_USER)
    }

    @Test
    fun `getValidToken returns user if well-formed and valid`() {
        assertThat(getValidToken(userAsString)).isEqualTo(user)
    }
}