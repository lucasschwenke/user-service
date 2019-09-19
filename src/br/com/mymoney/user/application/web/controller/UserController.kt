package br.com.mymoney.user.application.web.controller

import br.com.mymoney.user.application.web.response.UserResponse
import br.com.mymoney.user.domain.service.UserService
import io.ktor.application.ApplicationCall

class UserController(private val userService: UserService) {

    fun getUser(call: ApplicationCall): UserResponse {
        val userId = call.parameters["id"] ?: throw RuntimeException() // TODO: Error Exception

        return userService.getUser(userId)
            .let {
                UserResponse(
                    id = it.id,
                    name = it.name,
                    lastName = it.lastName,
                    email = it.email
                )
            }
    }

}