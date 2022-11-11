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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "id")
    var courses: MutableSet<Course> = mutableSetOf()
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
}