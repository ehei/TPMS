package com.ehei.tpms.server.model

data class FullTerm (
    var id: Long? = null,

    var title: String? = null,

    var startDate: String? = null,

    var endDate: String? = null,

    var courses: MutableList<FullCourse> = mutableListOf(),

    var user: User? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FullTerm) return false

        if (id != other.id) return false
        if (title != other.title) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (courses != other.courses) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (startDate?.hashCode() ?: 0)
        result = 31 * result + (endDate?.hashCode() ?: 0)
        result = 31 * result + courses.hashCode()
        result = 31 * result + (user?.hashCode() ?: 0)
        return result
    }
}