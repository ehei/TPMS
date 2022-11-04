package com.ehei.tpms.server.model

import org.springframework.data.annotation.Id
import java.util.*

data class Instructor(
    @Id
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val emailAddress: String
    )