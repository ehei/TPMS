package com.ehei.tpms.server.model

import java.io.Serializable

/**
 * Login
 *
 * @property username
 * @property password
 */
class Login(var username: String? = null,
            var password: String? = null
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Login) return false

        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username?.hashCode() ?: 0
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }
}