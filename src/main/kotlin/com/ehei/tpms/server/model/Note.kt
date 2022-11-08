package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
class Note (
    @Column(nullable = false)
    val text: String = "",

    @ManyToOne
    @JoinColumn(name = "fk_course")
    var course: Course? = null
) {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}