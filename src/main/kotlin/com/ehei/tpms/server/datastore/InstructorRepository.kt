package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Instructor
import org.springframework.data.jpa.repository.JpaRepository

interface InstructorRepository: JpaRepository<Instructor, Long>