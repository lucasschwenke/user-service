package br.com.mymoney.user.application.web.controller

import br.com.mymoney.user.application.web.response.UserResponse
import br.com.mymoney.user.domain.service.UserService
import io.ktor.application.ApplicationCall
import io.ktor.util.getOrFail

class UserController(private val userService: UserService) {

    fun getUser(call: ApplicationCall) = call.parameters.getOrFail("id")
        .run {
            userService.getUser(this)
        }.let {
                UserResponse(
                    id = it.id,
                    name = it.name,
                    lastName = it.lastName,
                    email = it.email
                )
            }
}