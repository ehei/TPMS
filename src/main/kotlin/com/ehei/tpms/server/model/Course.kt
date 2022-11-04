package com.ehei.tpms.server.model

import org.springframework.data.annotation.Id
import java.util.*

class Course (
    @Id
    val id: UUID,
    val title: String,
    val status: CourseStatus,
    val instructors: MutableSet<Instructor>,
    val assessments: MutableSet<Assessment>,
    val notes: MutableList<String>
)