package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * User repository
 *
 */
interface UserRepository: JpaRepository<User, Long>