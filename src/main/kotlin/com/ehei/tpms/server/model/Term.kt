package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
@Embeddable
data class Term (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String,
    var startDate: String? = null,
    var endDate: String? = null,

    @OneToMany(mappedBy = "term", cascade = [CascadeType.ALL])
    var courses: MutableSet<Course> = mutableSetOf()
)