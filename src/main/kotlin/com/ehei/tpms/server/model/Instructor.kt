package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
data class Instructor(
    @javax.persistence.Id
    @GeneratedValue
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val emailAddress: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val course: List<Course>
)