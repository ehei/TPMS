package com.ehei.tpms.server.model

import java.io.Serializable
import javax.persistence.*

/**
 * Course
 *
 * @property id
 * @property title
 * @property status
 * @property startDate
 * @property endDate
 * @property instructor_ids
 * @property assessment_ids
 * @property notes
 * @property userId
 */
@Entity
class Course(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String? = null,
    var status: CourseStatus? = null,
    var startDate: String? = null,
    var endDate: String? = null,

    @ElementCollection
    var instructor_ids: MutableList<Long> = mutableListOf(),

    @ElementCollection
    var assessment_ids: MutableList<Long> = mutableListOf(),

    @ElementCollection
    var notes: MutableList<String> = mutableListOf(),

    var userId: Long? = null

): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Course) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (status != other.status) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (instructor_ids != other.instructor_ids) return false
        if (assessment_ids != other.assessment_ids) return false
        if (notes != other.notes) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + instructor_ids.hashCode()
        result = 31 * result + assessment_ids.hashCode()
        result = 31 * result + notes.hashCode()
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }

}