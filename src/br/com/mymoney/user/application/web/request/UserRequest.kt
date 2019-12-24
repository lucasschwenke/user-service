package br.com.mymoney.user.application.web.request

import br.com.mymoney.user.application.web.request.validation.*
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_LAST_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_USER_EMAIL_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MAX_USER_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_LAST_NAME_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_USER_EMAIL_LENGTH
import br.com.mymoney.user.application.web.request.validation.ValidationRules.MIN_USER_NAME_LENGTH
import br.com.mymoney.user.domain.exception.ValidationException
import br.com.mymoney.user.domain.model.User

data class UserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val taxIdentifier: String
) {

    fun validation() {
        val validations = listOf(
            Validation("name", name).isNullOrBlank(),
            Validation("name", name).isValidLength(MIN_USER_NAME_LENGTH, MAX_USER_NAME_LENGTH),
            Validation("last_name", lastName).isNullOrBlank(),
            Validation("last_name", lastName).isValidLength(MIN_LAST_NAME_LENGTH, MAX_LAST_NAME_LENGTH),
            Validation("email", email).isNullOrBlank(),
            Validation("email", email).isValidLength(MIN_USER_EMAIL_LENGTH, MAX_USER_EMAIL_LENGTH),
            Validation("email", email).isValidEmail(),
            Validation("tax_identifier", taxIdentifier).isNullOrBlank(),
            Validation("tax_identifier", taxIdentifier).isValidTaxIdentifier()
        )

        val errorsMap = validations
            .filter {
                it.errorMessageList.isNotEmpty()
            }
            .associateBy(
                { it.fieldName }, { it.errorMessageList }
            )

        if (errorsMap.isNotEmpty()) {
            throw ValidationException(errorsMap)
        }
    }

    fun toUser() = User(
        name = this.name,
        lastName = this.lastName,
        email = this.email,
        taxIdentifier = this.taxIdentifier
    )

}
