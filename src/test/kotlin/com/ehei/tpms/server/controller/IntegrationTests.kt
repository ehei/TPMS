package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


@WebMvcTest(controllers = [TermController::class], excludeAutoConfiguration = [SecurityAutoConfiguration::class])
class IntegrationTests {

    @MockBean
    private lateinit var termRepository: TermRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val user = User(42, "testuser", "testpassword", "testrole")

    private val term = Term(id = null, title = "create this", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
    private val term1 = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
    private val term2 = Term(id = 2, title = "2nd", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
    private val term3 = Term(id = 3, title = "third", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)


    @BeforeEach
    fun setUp() {

        whenever(termRepository.findById(1)).thenReturn(Optional.of(term1))
        whenever(termRepository.findById(2)).thenReturn(Optional.of(term2))
        whenever(termRepository.findById(3)).thenReturn(Optional.of(term3))

        whenever(termRepository.findAll()).thenReturn(listOf(term1, term2, term3))

        whenever(termRepository.save(any<Term>())).thenReturn(term1)
    }

    @Test
    fun `If term requested exists, return Term and 200`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms/1")
            .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.title").value("First")
            )
    }

    @Test
    fun `If term requested does not exist, return a 404`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms/4")
            .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isNotFound
            )
    }

    @Test
    fun `get without an id gets list of all terms and total count in the header`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms")
            .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpect(header().longValue("X-Total-Count", 3))
            .andExpect(header().string("Access-Control-Expose-Headers", "X-Total-Count"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.*").isArray)
    }

    @Test
    fun `create does so`() {

        val jsonTerm = ObjectMapper().writeValueAsString(term)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/terms")
                .content(jsonTerm)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.title").value("First")
            )
    }

    @Test
    fun `update does so`() {

        val jsonTerm = ObjectMapper().writeValueAsString(term1)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/terms/1")
                .content(jsonTerm)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.title").value("First")
            )
    }

    @Test
    fun `delete does`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/terms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isOk
            )
    }

    @Test
    fun `delete with mismatchuser id returns not found`() {
        user.id = 31
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/terms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", ObjectMapper().writeValueAsString(user))
        )
            .andExpectAll(
                status().isNotFound
            )
    }
}