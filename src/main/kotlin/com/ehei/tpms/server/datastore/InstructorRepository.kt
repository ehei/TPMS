package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Instructor
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Instructor repository
 *
 */
interface InstructorRepository: JpaRepository<Instructor, Long>