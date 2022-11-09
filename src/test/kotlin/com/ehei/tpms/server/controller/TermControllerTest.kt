package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.EditableTerm
import com.ehei.tpms.server.model.Term
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.util.MultiValueMap
import java.util.*

class TermControllerTest {

    lateinit var termRepository: TermRepository
    lateinit var termController: TermController

    @BeforeEach
    internal fun setUp() {

        termRepository = mock()
        termController = TermController(termRepository)
    }

    @Test
    fun `findAll returns JSON data in the correct format`() {

        val term1 = Term("Aug 1, 2022", "July 2, 2022", "term 1")
        val term2 = Term("Sep 1, 2022", "Nov 2, 2022", "term 2")

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

        val term1 = Term("term 1","Aug 1, 2022", "July 2, 2022")
        val saveTerm1 = Term("term 2", "Aug 1, 2022", "July 2, 2022")

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
        val saved = argumentCaptor.firstValue

        assertThat(saved.title).isEqualTo("term 2")
        assertThat(saved.startDate).isEqualTo("Aug 1, 2022")
        assertThat(saved.endDate).isEqualTo("July 21, 2022")

        assertThat(savedAndFlushed.title).isEqualTo("term 2")
        assertThat(savedAndFlushed.startDate).isEqualTo("Aug 1, 2022")
        assertThat(savedAndFlushed.endDate).isEqualTo("July 2, 2022")
    }

    @Test
    fun `update ignores fields that do not have entries`() {

        val term1 = Term("term 1","Aug 1, 2022", "July 2, 2022")
        val saveTerm1 = Term("term 2", "Aug 1, 2022", "July 2, 2022")

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
}