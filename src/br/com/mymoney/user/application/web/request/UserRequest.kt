package br.com.mymoney.user.application.web.request

import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_LAST_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_USER_EMAIL_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_USER_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_LAST_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_USER_EMAIL_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_USER_NAME_LENGTH
import br.com.mymoney.user.domain.exception.BadRequestException
import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.util.ValidatorUtil

data class UserRequest(
    val id: String? = null,
    val name: String,
    val lastName: String,
    val email: String,
    val taxIdentifier: String
) {

    fun validation() {
        name.run {
            if (this.isBlank()) throw BadRequestException("The name is blank.")

            if (this.length < MIN_USER_NAME_LENGTH || this.length > MAX_USER_NAME_LENGTH)
                throw BadRequestException("The name is not a valid length.")
        }

        lastName.run {
            if (this.isBlank()) throw BadRequestException("The last_name is blank.")

            if (this.length < MIN_LAST_NAME_LENGTH || this.length > MAX_LAST_NAME_LENGTH)
                throw BadRequestException("The last_name is not a valid length.")
        }

        email.run {
            if (this.isBlank()) throw BadRequestException("The email is blank.")

            if (this.length < MIN_USER_EMAIL_LENGTH || this.length > MAX_USER_EMAIL_LENGTH)
                throw BadRequestException("The email is not a valid length.")

            if (!ValidatorUtil.validateEmail(this)) throw BadRequestException("The email $email is not valid.")
        }

        taxIdentifier.run {
            if (this.isBlank()) throw BadRequestException("The tax_identifier is blank.")

            if (!ValidatorUtil.validateTaxIdentifier(this))
                throw BadRequestException("The tax_identifier $taxIdentifier is not valid.")
        }
    }

    fun toUser() = User(
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        taxIdentifier = this.taxIdentifier
    )

}
