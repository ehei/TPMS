package com.ehei.tpms.server.model

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

@Entity
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