package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.UserRepository
import com.ehei.tpms.server.model.Login
import com.ehei.tpms.server.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping(path = ["authentication"])
@CrossOrigin("*")
class AuthenticationController(
    @Autowired val userRepository: UserRepository
) {

    @PostMapping
    @ResponseBody
    fun authenticate(@RequestBody login: Login): ResponseEntity<User>
    {
        return when {
            login.username.isNullOrBlank() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            login.password.isNullOrBlank() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            else -> {
                val firstOrNull: User? = userRepository.findAll()
                    .firstOrNull { record -> record.username == login.username }

                val user = when (firstOrNull) {
                    null -> userRepository.save(User(username = login.username, password = login.password, fullName = login.username))
                    else -> firstOrNull
                }

                if (user.password.equals(login.password)) {
                    ResponseEntity.ok(user)
                }
                else {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                }
            }
        }
    }
}
val objectMapper = ObjectMapper()

fun isAuthorized(token: String?, id: Long?): Boolean {

    if (token.isNullOrBlank())
        return false

    val user = objectMapper.readValue(token, User::class.java)
    if (user.role.isBlank() || user.role == "guest")
        return false

    return user.id == id
}

fun userId(token: String?): Long {
    if (token.isNullOrBlank())
        return -1
    val user = objectMapper.readValue(token, User::class.java)

    return user?.id!!
}