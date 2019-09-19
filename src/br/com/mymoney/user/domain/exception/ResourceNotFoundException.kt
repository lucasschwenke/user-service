package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

class ResourceNotFoundException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatusCode.NotFound

    override fun apiError() = ApiError.RESOURCE_NOT_FOUND

    override fun userResponseMessage() = "$message"

}