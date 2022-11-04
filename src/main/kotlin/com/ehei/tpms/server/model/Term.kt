package com.ehei.tpms.server.model

import org.springframework.data.annotation.Id
import java.util.*

data class Term (
    @Id
    val id: UUID,
    val startDate: String,
    val endDate: String,
    val title: String
    )