package com.ehei.tpms.server.model

/**
 * Full course
 *
 * Representation of the course with all sub items
 *
 * @property id
 * @property title
 * @property status
 * @property startDate
 * @property endDate
 * @property instructors
 * @property assessments
 * @property notes
 */
data class FullCourse(
    var id: Long? = null,

    var title: String? = null,
    var status: CourseStatus? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var instructors: MutableList<Instructor> = mutableListOf(),
    var assessments: MutableList<Assessment> = mutableListOf(),
    var notes: MutableList<String> = mutableListOf()
) {
    companion object {
        fun fromCourse(course: Course): FullCourse = FullCourse(
            id = course.id,
            title = course.title,
            status = course.status,
            startDate = course.startDate,
            endDate = course.endDate,
            notes = course.notes
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FullCourse) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (status != other.status) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (instructors != other.instructors) return false
        if (assessments != other.assessments) return false
        if (notes != other.notes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + instructors.hashCode()
        result = 31 * result + assessments.hashCode()
        result = 31 * result + notes.hashCode()
        return result
    }
}