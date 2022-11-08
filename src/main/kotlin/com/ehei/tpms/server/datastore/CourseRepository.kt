package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Long>