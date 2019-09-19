package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

class BadRequestException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatusCode.BadRequest

    override fun apiError() = ApiError.BAD_REQUEST

    override fun userResponseMessage() = "$message"
}