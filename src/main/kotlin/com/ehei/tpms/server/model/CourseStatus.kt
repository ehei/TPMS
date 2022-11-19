package com.ehei.tpms.server.model

/**
 * Course status
 *
 * @property title
 */
enum class CourseStatus(val title: String) {
    PlanToTake("Plan to Take"),
    Completed("Completed"),
    Dropped("Dropped"),
    InProgress("In Progress")
}