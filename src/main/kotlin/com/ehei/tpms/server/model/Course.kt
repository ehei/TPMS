package com.ehei.tpms.server.model

import net.minidev.json.annotate.JsonIgnore
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class Course (
    val title: String,
    val status: CourseStatus,

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "course_to_instructor",
        joinColumns = [JoinColumn(name = "instructor_id")],
        inverseJoinColumns = [JoinColumn(name = "course_id")])
    var instructors: MutableSet<Instructor> = mutableSetOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "id")
    val assessments: MutableSet<Assessment> = mutableSetOf(),

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "id")
    val notes: MutableSet<Note> = mutableSetOf(),

    @ManyToOne
    @JoinColumn(name = "fk_term")
    var term: Term? = null
) {
    fun add(note: Note) {
        if (note.course != this) {
            note.course?.notes?.remove(note)
            notes.add(note)
            note.course = this
        }
    }

    fun add(assessment: Assessment) {
        if (assessment.course != this) {
            assessment.course?.assessments?.remove(assessment)
            assessments.add(assessment)
            assessment.course = this
        }
    }

    fun add(instructor: Instructor) {
        instructors.add(instructor)
        instructor.courses.add(this)
    }

    fun remove(note: Note) {
        if (note.course == this) {
            notes.remove(note)
            note.course = null
        }
    }

    fun remove(assessment: Assessment) {
        if (assessment.course == this) {
            assessments.remove(assessment)
            assessment.course = null
        }
    }

    fun remove(instructor: Instructor) {
        if (instructor.courses.contains(this)) {
            instructors.remove(instructor)
            instructor.courses.remove(this)
        }
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}