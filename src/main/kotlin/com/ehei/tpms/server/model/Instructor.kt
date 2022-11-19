package com.ehei.tpms.server.model

import java.io.Serializable
import javax.persistence.*

/**
 * Instructor
 *
 * @property id
 * @property name
 * @property phoneNumber
 * @property emailAddress
 * @property userId
 */
@Entity
class Instructor(
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String? = null,
    var phoneNumber: String? = null,
    var emailAddress: String? = null,
    var userId: Long? = null

): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Instructor) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (phoneNumber != other.phoneNumber) return false
        if (emailAddress != other.emailAddress) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (phoneNumber?.hashCode() ?: 0)
        result = 31 * result + (emailAddress?.hashCode() ?: 0)
        result = 31 * result + (userId?.hashCode() ?: 0)
        return result
    }

}