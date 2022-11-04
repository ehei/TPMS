package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Course
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseRepository: JpaRepository<Course, UUID>