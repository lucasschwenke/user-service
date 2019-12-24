package br.com.mymoney.user.application.web.response

import br.com.mymoney.user.domain.model.User

data class UserResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val taxIdentifier: String
) {

    constructor(user: User) : this(
        user.id!!,
        user.name,
        user.lastName,
        user.email,
        user.taxIdentifier
    )
}
