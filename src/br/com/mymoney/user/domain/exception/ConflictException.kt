package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

class ConflictException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatusCode.Conflict

    override fun apiError() = ApiError.CONFLICT

    override fun userResponseMessage() = "$message"
}