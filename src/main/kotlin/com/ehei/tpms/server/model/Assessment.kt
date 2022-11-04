package com.ehei.tpms.server.model

import org.springframework.data.annotation.Id
import java.util.*

data class Assessment (
    @Id
    val id: UUID,
    val title: String,
    val performance: Boolean
    )