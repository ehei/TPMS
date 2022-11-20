package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.kotlin.*
import org.springframework.http.HttpStatus
import java.util.*

class TermControllerTest {

    private lateinit var termRepository: TermRepository
    private lateinit var termController: TermController

    private val user = User(1, "a", "b", "user")
    private val user2 = User(200, "a", "b", "guest")
    private lateinit var userAsString: String
    private lateinit var user2AsString: String

    @BeforeEach
    fun setUp() {

        termRepository = mock()
        termController = TermController(termRepository)

        userAsString = ObjectMapper().writeValueAsString(user)
        user2AsString = ObjectMapper().writeValueAsString(user2)
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

        assertThat(term.userId).isEqualTo(term1.userId)
        assertThat(createdTerm).isEqualTo(term1)
    }

    @Test
    fun `updates term`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)

        val captor = ArgumentCaptor.forClass(Term::class.java)
        whenever(termRepository.findById(1L)).thenReturn(Optional.of(term1))
        whenever(termRepository.save(any<Term>())).thenReturn(term1)

        val updatedTerm: Term = termController.update(userAsString,1, term1).body!!

        assertThat(updatedTerm.title).isEqualTo(term1.title)
        assertThat(updatedTerm.startDate).isEqualTo(term1.startDate)
        assertThat(updatedTerm.endDate).isEqualTo(term1.endDate)
        assertThat(updatedTerm.userId).isEqualTo(term1.userId)

        verify(termRepository).findById(1L)
        verify(termRepository).save(captor.capture())

        assertThat(captor.value.title).isEqualTo(term1.title)
        assertThat(captor.value.startDate).isEqualTo(term1.startDate)
        assertThat(captor.value.endDate).isEqualTo(term1.endDate)
    }

    @Test
    fun `returns not found if term has different userid`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = 51223)

        whenever(termRepository.findById(1)).thenReturn(Optional.of(term1))

        assertThat(termController.update(userAsString,1, term1).statusCodeValue).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `returns not found if term is not found`() {
        val term1 = Term(id = 1, title = "third updated", startDate = "2022/12/31", endDate = "2023/01/30", userId = 51223)

        whenever(termRepository.findById(1)).thenReturn(Optional.ofNullable(null))

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

    @Test
    fun `if bad token is passed in, do not create a new term`() {
        val term = Term(id = null, title = "third", startDate = "2022/12/31", endDate = "2023/01/30")
        val term1 = Term(id = 1, title = "third", startDate = "2022/12/31", endDate = "2023/01/30", userId = user.id)
        whenever(termRepository.save(term)).thenReturn(term1)

        val response = termController.create("", term)

        assertThat(response.statusCodeValue).isEqualTo(HttpStatus.BAD_REQUEST.value())
        verify(termRepository, never()).save(term)
    }

    @Test
    fun `if user is not found, do not return a term`() {
        val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = 23)
        whenever(termRepository.findById(1)).thenReturn(Optional.of(term))

        assertThat(termController.get(user2AsString, 1).statusCodeValue).isEqualTo(HttpStatus.NOT_FOUND.value())
    }

    @Test
    fun `if user is found but does not have the correct role, do not return a term`() {
        val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30", userId = user2.id)

        whenever(termRepository.findById(1)).thenReturn(Optional.of(term))

        assertThat(termController.get(user2AsString, 1).statusCodeValue).isEqualTo(HttpStatus.NOT_FOUND.value())
    }
}