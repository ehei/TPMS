package com.ehei.tpms.server.model

import net.minidev.json.annotate.JsonIgnore
import javax.persistence.*

@Entity
data class Term (
    var title: String,
    var startDate: String,
    var endDate: String,

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "id")
    val courses: MutableSet<Course> = mutableSetOf()
    ) {

    fun add(course: Course) {

        if (course.term == null) {
            courses.add(course)
            course.term = this
        }
    }

    fun remove(course: Course) {
        if (course.term == this) {
            courses.remove(course)
            course.term = null
        }
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}