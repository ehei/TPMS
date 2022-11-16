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
