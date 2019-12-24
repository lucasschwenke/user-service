package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

class ValidationException(private val errorsDetails: Map<String, List<String>>) :
    ApiException(message = "A validation error occurred in fields: $errorsDetails") {

    override fun httpStatus() = HttpStatusCode.BadRequest

    override fun apiError() = ApiError.BAD_REQUEST

    override fun userResponseMessage(): String = "Invalid fields: ${errorsDetails.keys}"

    override fun details(): Map<String, List<String>> = errorsDetails
}