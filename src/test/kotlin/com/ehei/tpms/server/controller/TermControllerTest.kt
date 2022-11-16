package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import java.util.*

class TermControllerTest {

    private lateinit var termRepository: TermRepository
    private lateinit var termController: TermController

    private val user = User(1, "a", "b", "c")
    private lateinit var userAsString: String

    @BeforeEach
    fun setUp() {

        termRepository = mock()
        termController = TermController(termRepository)

        userAsString = ObjectMapper().writeValueAsString(user)
    }

    @Test
    fun `returns term as JSON`() {

        val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        whenever(termRepository.findById(1)).thenReturn(Optional.of(term))

        val foundTerm: Term? = termController.get(userAsString, 1).body

        verify(termRepository).findById(1)
        assertThat(foundTerm).isNotNull
        assertThat(foundTerm!!).isEqualTo(term)
    }

    @Test
    fun `returns Not Found if user id does not match`() {

        val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = 23)
        whenever(termRepository.findById(1)).thenReturn(Optional.of(term))

        assertThat(termController.get(userAsString, 1).statusCodeValue).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `returns list of terms as JSON, filtered by logged in user`() {

        val term1 = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        val term2 = Term(id = 2, title = "2nd", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        val term3 = Term(id = 3, title = "third", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        val termDifferentUserId = Term(id = 3, title = "third", startDate = "2022/12/31", endDate = "2023/01/30", userId = 89)
        whenever(termRepository.findAll()).thenReturn(listOf(term1, term2, term3, termDifferentUserId))

        val foundTerms: List<Term>? = termController.getAll(userAsString).body

        assertThat(foundTerms).containsExactlyInAnyOrder(term1, term2, term3)
    }

    @Test
    fun `creates a new term with user id`() {
        val term = Term(id = null, title = "third", startDate = "2022/12/31", endDate = "2023/01/30")
        val term1 = Term(id = 1, title = "third", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        whenever(termRepository.save(term)).thenReturn(term1)

        val createdTerm: Term = termController.create(userAsString, term).body!!

        verify(termRepository).save(term)

        assertThat(createdTerm).isEqualTo(term1)
    }

    @Test
    fun `updates term`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)

        whenever(termRepository.findById(1L)).thenReturn(Optional.of(term1))
        whenever(termRepository.save(any<Term>())).thenReturn(term1)

        val updatedTerm: Term = termController.update(userAsString,1, term1).body!!

        assertThat(updatedTerm).isNotNull

        verify(termRepository).findById(1L)
        verify(termRepository).save(any<Term>())
    }

    @Test
    fun `returns not found if term has different userid`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = 51223)

        whenever(termRepository.findById(1)).thenReturn(Optional.of(term1))

        assertThat(termController.update(userAsString,1, term1).statusCodeValue).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `deletes term`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)

        whenever(termRepository.findById(1)).thenReturn(Optional.ofNullable(term1))
        whenever(termRepository.delete(term1)).thenAnswer {
        }

        termController.delete(userAsString, 1)

        verify(termRepository).deleteById(1)
    }

    @Test
    fun `returns not found if userid does not match`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = 123)

        whenever(termRepository.findById(1)).thenReturn(Optional.ofNullable(term1))

        assertThat(termController.delete(userAsString, 1).statusCodeValue)
            .isEqualTo(HttpStatus.NOT_FOUND.value())
    }
}