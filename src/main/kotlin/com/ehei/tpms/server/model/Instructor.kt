package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
class Instructor(
    val name: String,
    val phoneNumber: String,
    val emailAddress: String,


    @ManyToMany(mappedBy = "instructors")
    var courses: MutableSet<Course> = mutableSetOf()
) {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}