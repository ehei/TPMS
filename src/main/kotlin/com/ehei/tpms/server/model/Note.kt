package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
data class Note (
    @javax.persistence.Id
    @GeneratedValue
    val id: Long,
    val text: String,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val course: Course
)