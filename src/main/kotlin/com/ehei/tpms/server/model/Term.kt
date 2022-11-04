package com.ehei.tpms.server.model

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.OneToMany

@Entity
data class Term (
    @javax.persistence.Id
    @GeneratedValue
    val id: Long,
    val startDate: String,
    val endDate: String,
    val title: String,

    @JsonIgnore
    @OneToMany
    val courses: List<Course>
    )