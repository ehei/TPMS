package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
class Instructor(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var phoneNumber: String,
    var emailAddress: String,


    @ManyToMany(mappedBy = "instructors")
    var courses: MutableSet<Course> = mutableSetOf()
)