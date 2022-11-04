package com.ehei.tpms.server.model

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

@Entity
data class Course (
    @javax.persistence.Id
    @GeneratedValue
    val id: Long,
    val title: String,
    val status: CourseStatus,

    @JsonIgnore
    @OneToMany
    val instructors: List<Instructor>,

    @JsonIgnore
    @OneToMany
    val assessments: List<Assessment>,

    @JsonIgnore
    @OneToMany
    val notes: List<Note>,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val term: Term
) {
}