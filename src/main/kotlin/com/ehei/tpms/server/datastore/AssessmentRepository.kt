package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Assessment
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Assessment repository
 *
 */
interface AssessmentRepository: JpaRepository<Assessment, Long>