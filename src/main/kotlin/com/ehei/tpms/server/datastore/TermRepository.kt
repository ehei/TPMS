package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Term
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TermRepository: JpaRepository<Term, Long> {
}