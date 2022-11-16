package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>