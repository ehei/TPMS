package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Assessment
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AssessmentRepository: JpaRepository<Assessment, UUID>