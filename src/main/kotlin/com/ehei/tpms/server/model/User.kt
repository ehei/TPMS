package com.ehei.tpms.server.model

import java.io.Serializable
import javax.persistence.*

/**
 * Unknown User - used in place of null checks
 */
val UNKNOWN_USER = User(id = -2, role = "unknown", fullName = "UNKNOWN USER")

/**
 * User
 *
 * @property id
 * @property username
 * @property password
 * @property role
 * @property fullName
 * @constructor Create User
 */
@Entity
@Table(name = "tpms_users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var role: String = "user",
    var fullName: String? = null,
    ): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (role != other.role) return false
        if (fullName != other.fullName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + role.hashCode()
        result = 31 * result + (fullName?.hashCode() ?: 0)
        return result
    }
}