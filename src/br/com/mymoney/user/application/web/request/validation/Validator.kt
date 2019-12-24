package br.com.mymoney.user.application.web.request.validation

import br.com.mymoney.user.domain.util.ValidatorUtil

data class Validation<T>(
    val fieldName: String,
    val fieldValue: T?,
    val errorMessageList: MutableList<String> = mutableListOf()
)

fun Validation<String>.isNullOrBlank(): Validation<String> {
    if (this.fieldValue.isNullOrBlank()) {
        errorMessageList.add("The field is null or blank.")
    }

    return this
}

fun Validation<String>.isValidLength(min: Int? = null, max: Int? = null): Validation<String> {
    if (max != null && this.fieldValue!!.length > max) {
        this.errorMessageList.add("should be less than $max")
    }

    if (min != null && this.fieldValue!!.length < min) {
        this.errorMessageList.add("should be greater than $min")
    }

    return this
}

fun Validation<String>.isValidEmail(): Validation<String> {
    if (this.fieldValue != null && !ValidatorUtil.validateEmail(this.fieldValue)) {
        this.errorMessageList.add("The email ${this.fieldValue} is not valid.")
    }

    return this
}

fun Validation<String>.isValidTaxIdentifier(): Validation<String> {
    if (this.fieldValue != null && !ValidatorUtil.validateTaxIdentifier(this.fieldValue)) {
        this.errorMessageList.add("The tax identifier ${this.fieldValue} is not valid.")
    }

    return this
}