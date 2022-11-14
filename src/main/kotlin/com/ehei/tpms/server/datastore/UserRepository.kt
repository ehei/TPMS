package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    @Query("select u from #{#entityName} u where u.username = :username")
    fun findByUsername(@Param("username") username: String) : Optional<User>

}