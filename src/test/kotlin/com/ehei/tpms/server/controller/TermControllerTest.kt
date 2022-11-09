package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.TermRepository
import com.ehei.tpms.server.model.Term
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

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
}