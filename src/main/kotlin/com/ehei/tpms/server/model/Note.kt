package com.ehei.tpms.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Embeddable
class Note (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var text: String = "",

    @ManyToOne
    @JsonIgnoreProperties(value = ["notes"], allowSetters = true)
    var course: Course? = null
)