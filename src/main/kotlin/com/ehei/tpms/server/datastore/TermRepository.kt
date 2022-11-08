package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.Term
import org.springframework.data.jpa.repository.JpaRepository

interface TermRepository: JpaRepository<Term, Long>