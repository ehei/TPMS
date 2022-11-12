package com.ehei.tpms.server.model

import javax.persistence.*

@Entity
@Embeddable
class Instructor(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var phoneNumber: String,
    var emailAddress: String,
)