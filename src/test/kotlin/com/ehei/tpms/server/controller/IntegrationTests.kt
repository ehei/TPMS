package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    private lateinit var termController: TermController

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {

        val term1 = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30")
        val term2 = Term(id = 2, title = "2nd", startDate = "2022/12/31", endDate = "2023/01/30")
        val term3 = Term(id = 3, title = "third", startDate = "2022/12/31", endDate = "2023/01/30")

        whenever(termRepository.findById(1)).thenReturn(Optional.of(term1))
        whenever(termRepository.findById(2)).thenReturn(Optional.of(term2))
        whenever(termRepository.findById(3)).thenReturn(Optional.of(term3))

        whenever(termRepository.findAll()).thenReturn(listOf(term1, term2, term3))
    }

    @Test
    fun `If term requested exists, return Term and 200`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms/1"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                jsonPath("$.title").value("First")
            )
    }

    @Test
    fun `If term requested does not exist, return a 404`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms/4"))
            .andExpectAll(
                status().isNotFound
            )
    }

    @Test
    fun `get without an id gets list of all terms`() {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/terms"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.*").isArray)
    }
}