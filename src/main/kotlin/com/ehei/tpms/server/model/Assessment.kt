package com.ehei.tpms.server.model

import java.io.Serializable
import javax.persistence.*

/**
 * Assessment
 *
 * @property id
 * @property title
 * @property startDate
 * @property endDate
 * @property performance
 * @property userId
 */
@Entity
class Assessment (
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var title: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,

    var performance: Boolean = false,

    var userId: Long? = null

): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Assessment) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (performance != other.performance) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + performance.hashCode()
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }

}