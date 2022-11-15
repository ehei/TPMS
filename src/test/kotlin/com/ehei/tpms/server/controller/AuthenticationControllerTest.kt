package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.UserRepository
import com.ehei.tpms.server.model.Login
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@WebMvcTest(controllers = [AuthenticationController::class], excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class AuthenticationControllerTest {

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val user = User(1, "a", "b", "c")
    private lateinit var userAsString: String

    @BeforeEach
    fun setUp() {
        userAsString = ObjectMapper().writeValueAsString(user)
    }

    @Test
    fun `null user name returns 401`() {

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = null, password = "what"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        )
    }

    @Test
    fun `empty user name returns 401`() {

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "", password = "what"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        )
    }

    @Test
    fun `empty password returns 401`() {

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "who", password = ""))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        )
    }

    @Test
    fun `null password returns 401`() {

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "who", password = null))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        )
    }

    @Test
    fun `passing valid username and password with mismatch password throw 401`() {

        val user = User(id = 1, username = "who", password = "not the same")
        whenever(userRepository.findAll()).thenReturn(listOf(user))

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "who", password = "what"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpectAll(
            MockMvcResultMatchers.status().isUnauthorized
        )
    }

    @Test
    fun `passing valid username and matching password returns the User and 200`() {

        val user = User(id = 1, username = "who", password = "what")
        whenever(userRepository.findAll()).thenReturn(listOf(user))

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "who", password = "what"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.username").value("who"),
                MockMvcResultMatchers.jsonPath("$.password").value("what")
            )
    }

    @Test
    fun `passing valid username and password but user is not found, add user and return 200`() {

        val user = User(id = 1, username = "who", password = "what", fullName = "where")
        whenever(userRepository.findByUsername("something")).thenReturn(Optional.empty())

        whenever(userRepository.save(User(username = "something", password = "what", fullName = "something"))).thenReturn(user)

        val jsonValue = ObjectMapper().writeValueAsString(Login(username = "something", password = "what"))

        mockMvc.perform(
            MockMvcRequestBuilders.post("/authentication")
                .content(jsonValue)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpectAll(
                MockMvcResultMatchers.status().isOk,
                MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON),
                MockMvcResultMatchers.jsonPath("$.username").value("who"),
                MockMvcResultMatchers.jsonPath("$.password").value("what"),
                MockMvcResultMatchers.jsonPath("$.fullName").value("where")
            )

        verify(userRepository).save(User(username = "something", password = "what", fullName = "something"))
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
    fun `if token is null or blank return -1`() {

        assertThat(userId(null)).isEqualTo(-1)
        assertThat(userId("")).isEqualTo(-1)
        assertThat(userId("   ")).isEqualTo(-1)
    }

    @Test
    fun `return id value of user`() {

        assertThat(userId(userAsString)).isEqualTo(user.id)
    }
}