package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

abstract class ApiException : Exception {

    constructor(cause: Throwable) : super(cause)
    constructor(message: String) : super(message)

    abstract fun httpStatus(): HttpStatusCode
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String

    fun createErrorResponse() =
        ErrorResponse(
            apiError = apiError(),
            errorDetails = userResponseMessage()
        )
}

data class ErrorResponse(
    val apiError: ApiError,
    val errorDetails: String
)

enum class ApiError {
    RESOURCE_NOT_FOUND,
    BAD_REQUEST,
    PERSISTENCE_FAILED
}