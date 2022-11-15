package com.ehei.tpms.server.model

import java.io.Serializable
import javax.persistence.*

@Entity
class Term(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String? = null,

    var startDate: String? = null,

    var endDate: String? = null,

    @ElementCollection
    var course_ids: MutableList<Long> = mutableListOf(),

    var userId: Long? = null

): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Term) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (course_ids != other.course_ids) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + course_ids.hashCode()
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }

}