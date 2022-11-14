package com.ehei.tpms.server.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Embeddable
class Course(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(length = 200)
    var title: String,
    var status: CourseStatus = CourseStatus.PlanToTake,
    @Column(length = 50)
    var startDate: String? = null,
    @Column(length = 50)
    var endDate: String? = null,

    @ElementCollection(targetClass = Instructor::class)
    var instructors: MutableSet<Instructor> = mutableSetOf(),

    @ElementCollection(targetClass = Assessment::class)
    var assessments: MutableSet<Assessment> = mutableSetOf(),

    @ElementCollection(targetClass = Note::class)
    var notes: MutableSet<Note> = mutableSetOf(),

    @ManyToOne
    @JsonIgnoreProperties(value = ["courses"], allowSetters = true)
    @JoinColumn(name = "term_id")
    var term: Term? = null
)