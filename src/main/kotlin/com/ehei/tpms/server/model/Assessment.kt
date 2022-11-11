package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
class Assessment (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var startDate: String? = null,
    var endDate: String? = null,

    var performance: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "fk_course")
    var course: Course? = null
)