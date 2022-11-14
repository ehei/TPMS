package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    fun `returns term as JSON`() {

        val term = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30")
        whenever(termRepository.findById(1)).thenReturn(Optional.of(term))

        val foundTerm: Term? = termController.getTerm(1).body

        verify(termRepository).findById(1)
        assertThat(foundTerm).isNotNull
        assertThat(foundTerm!!).isEqualTo(term)
    }

    @Test
    fun `returns list of terms as JSON`() {

        val term1 = Term(id = 1, title = "First", startDate = "2022/12/31", endDate = "2023/01/30")
        val term2 = Term(id = 2, title = "2nd", startDate = "2022/12/31", endDate = "2023/01/30")
        val term3 = Term(id = 3, title = "third", startDate = "2022/12/31", endDate = "2023/01/30")
        whenever(termRepository.findAll()).thenReturn(listOf(term1, term2, term3))

        val foundTerms: List<Term>? = termController.getTerms().body

        verify(termRepository).findAll()

        assertThat(foundTerms).containsExactlyInAnyOrder(term1, term2, term3)
    }
}