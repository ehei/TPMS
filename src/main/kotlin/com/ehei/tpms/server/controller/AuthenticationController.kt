package com.ehei.tpms.server.controller

import com.ehei.tpms.server.datastore.UserRepository
import com.ehei.tpms.server.model.Login
import com.ehei.tpms.server.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

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
                val found = userRepository.findByUsername(login.username!!).orElse(
                    userRepository.save(User(username = login.username, password = login.password, fullName = login.username))
                )
                if (found.password.equals(login.password)) {
                    ResponseEntity.ok(found)
                }
                else {
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
                }
            }
        }
    }
}