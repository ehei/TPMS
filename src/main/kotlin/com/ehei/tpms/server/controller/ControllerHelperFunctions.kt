package com.ehei.tpms.server.controller

import com.ehei.tpms.server.model.UNKNOWN_USER
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity

private val countHeader = "X-Total-Count"
private val exposeHeader = "Access-Control-Expose-Headers"

val objectMapper = ObjectMapper()

fun getValidToken(token: String?): User =
    when {
        token.isNullOrBlank() -> UNKNOWN_USER
        else -> try {
            objectMapper.readValue(token, User::class.java)
        } catch (exception: Exception) {
            UNKNOWN_USER
        }
    }

fun isAuthorized(token: String?, id: Long?): Boolean {

    val user = getValidToken(token)
    if (user.role.isBlank() || user.role == "guest")
        return false

    return user.id == id
}

fun userId(token: String?): Long = getValidToken(token).id!!

fun <T> createListResponse(findAll: List<T>) : ResponseEntity<List<T>> =
    ResponseEntity.ok()
        .header(countHeader, "" + findAll.size)
        .header(exposeHeader, countHeader)
        .body(findAll)

