package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
@Embeddable
class Assessment (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var startDate: String? = null,
    var endDate: String? = null,

    var performance: Boolean = false,
)