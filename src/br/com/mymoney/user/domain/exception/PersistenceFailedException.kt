package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

class PersistenceFailedException(e: Exception) : ApiException(e) {

    override fun httpStatus() = HttpStatusCode.InternalServerError

    override fun apiError() = ApiError.PERSISTENCE_FAILED

    override fun userResponseMessage() = "Persistence failed: Could not save the resource."
}