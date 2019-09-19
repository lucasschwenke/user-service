package br.com.mymoney.user.application.web.request

import br.com.mymoney.user.domain.model.User

data class UserRequest(val name: String,
                       val lastName: String,
                       val email: String,
                       val taxIdentifier: String
) {

    fun toUser() = User(
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        taxIdentifier = this.taxIdentifier
    )

}
