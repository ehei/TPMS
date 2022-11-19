package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Course
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Course repository
 *
 */
interface CourseRepository: JpaRepository<Course, Long>