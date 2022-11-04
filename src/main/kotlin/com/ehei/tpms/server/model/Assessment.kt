package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
data class Assessment (
    @javax.persistence.Id
    @GeneratedValue
    val id: Long,
    val title: String,
    val performance: Boolean,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val course: Course
)