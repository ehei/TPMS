package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
class Assessment (
    val title: String,
    val performance: Boolean,

    @ManyToOne
    @JoinColumn(name = "fk_course")
    var course: Course? = null
) {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}