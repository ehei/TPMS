package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import com.ehei.tpms.server.model.TermBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*

class TermControllerTest {

    private lateinit var termRepository: TermRepository
    private lateinit var termController: TermController

    @BeforeEach
    fun setUp() {

        termRepository = mock()
        termController = TermController(termRepository)
    }

    @Test
    fun `findAll returns JSON data in the correct format`() {

        val term1 = Term(startDate = "Aug 1, 2022", endDate = "July 2, 2022", title = "term 1")
        val term2 = Term(startDate = "Sep 1, 2022", endDate = "Nov 2, 2022", title = "term 2")

        whenever(termRepository.findAll()).thenReturn(
            mutableListOf(
                term1,
                term2
            )
        )

        val all = termController.getAll()

        assertThat(all.content).containsExactlyInAnyOrder(term1, term2)
        assertThat(all.totalElements).isEqualTo(2)
    }

    @Test
    fun `Asking for term by id passes through to the repository`() {

        val someNumber: Long = 12

        termController.get(someNumber)

        verify(termRepository).findById(someNumber)
    }

    @Test
    fun `update saves term`() {

        val term1 = Term(title = "term 1",startDate = "Aug 1, 2022", endDate = "July 2, 2022")
        val saveTerm1 = Term(title = "term 2", startDate = "Aug 1, 2022", endDate = "July 2, 2022")

        val argumentCaptor = argumentCaptor<Term>()
        whenever(termRepository.findById(323)).thenReturn(Optional.of(term1))
        whenever(termRepository.saveAndFlush(argumentCaptor.capture())).thenReturn(saveTerm1)

        val paramMap: Map<String, List<String>> = mapOf(
            "startDate" to listOf("Aug 12, 2022"),
            "endDate" to listOf("July 21, 2022"),
            "title" to listOf("term 2"),
            "id" to listOf("323")
        )

        val savedAndFlushed = termController.update(323, paramMap)

        verify(termRepository).findById(323)

        assertThat(savedAndFlushed.title).isEqualTo("term 2")
        assertThat(savedAndFlushed.startDate).isEqualTo("Aug 1, 2022")
        assertThat(savedAndFlushed.endDate).isEqualTo("July 2, 2022")
    }

    @Test
    fun `update ignores fields that do not have entries`() {

        val term1 = Term(title = "term 1",startDate = "Aug 1, 2022", endDate = "July 2, 2022")
        val saveTerm1 = Term(title = "term 2", startDate = "Aug 1, 2022", endDate = "July 2, 2022")

        val argumentCaptor = argumentCaptor<Term>()
        whenever(termRepository.findById(323)).thenReturn(Optional.of(term1))
        whenever(termRepository.saveAndFlush(argumentCaptor.capture())).thenReturn(saveTerm1)

        val paramMap: Map<String, List<String>> = mapOf(
            "endDate" to listOf("Oct 4, 2022"),
            "id" to listOf("323")
        )

        val savedAndFlushed = termController.update(323, paramMap)

        verify(termRepository).findById(323)
        val saved = argumentCaptor.firstValue

        assertThat(saved.title).isEqualTo("term 1")
        assertThat(saved.startDate).isEqualTo("Aug 1, 2022")
        assertThat(saved.endDate).isEqualTo("Oct 4, 2022")

        assertThat(savedAndFlushed.title).isEqualTo("term 2")
        assertThat(savedAndFlushed.startDate).isEqualTo("Aug 1, 2022")
        assertThat(savedAndFlushed.endDate).isEqualTo("July 2, 2022")
    }

    @Test
    fun `insert saves new term`() {

        val saveTerm1 = Term(title = "new term", startDate = "Aug 12, 2022", endDate = "July 21, 2022")

        val argumentCaptor = argumentCaptor<Term>()
        whenever(termRepository.saveAndFlush(argumentCaptor.capture())).thenReturn(saveTerm1)

        val savedAndFlushed = termController.insert(TermBody(saveTerm1.title, saveTerm1.startDate, saveTerm1.endDate))
        val passedIn = argumentCaptor.firstValue

        assertThat(passedIn.title).isEqualTo(saveTerm1.title)
        assertThat(passedIn.startDate).isEqualTo(saveTerm1.startDate)
        assertThat(passedIn.endDate).isEqualTo(saveTerm1.endDate)

        assertThat(savedAndFlushed.title).isEqualTo("new term")
        assertThat(savedAndFlushed.startDate).isEqualTo("Aug 12, 2022")
        assertThat(savedAndFlushed.endDate).isEqualTo("July 21, 2022")
    }
}