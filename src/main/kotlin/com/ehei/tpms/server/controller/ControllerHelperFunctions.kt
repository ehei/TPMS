package com.ehei.tpms.server.controller

import com.ehei.tpms.server.model.UNKNOWN_USER
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity

private val countHeader = "X-Total-Count"
private val exposeHeader = "Access-Control-Expose-Headers"

val objectMapper = ObjectMapper()

/**
 * Get valid token
 *
 * @param token
 * @return
 */
fun getValidToken(token: String?): User =
    try {
        objectMapper.readValue(token, User::class.java)
    } catch (exception: Exception) {
        UNKNOWN_USER
    }
/**
 * Is authorized
 *
 * @param token
 * @param id
 * @return
 */
fun isAuthorized(token: String?, id: Long?): Boolean {

    val user = getValidToken(token)
    if (user.role == "guest")
        return false

    return user.id == id
}

/**
 * User id
 *
 * @param token
 * @return
 */
fun userId(token: String?): Long = getValidToken(token).id!!

/**
 * Create list response
 *
 * @param T
 * @param findAll
 * @return
 */
fun <T> createListResponse(findAll: List<T>) : ResponseEntity<List<T>> =
    ResponseEntity.ok()
        .header(countHeader, "" + findAll.size)
        .header(exposeHeader, countHeader)
        .body(findAll)

