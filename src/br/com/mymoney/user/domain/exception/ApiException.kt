package br.com.mymoney.user.domain.exception

import io.ktor.http.HttpStatusCode

abstract class ApiException : Exception {

    constructor(cause: Throwable) : super(cause)
    constructor(message: String) : super(message)

    abstract fun httpStatus(): HttpStatusCode
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String
    open fun details() = emptyMap<String, List<String>>()

    fun createErrorResponse() =
        ErrorResponse(
            apiError = apiError(),
            message = userResponseMessage(),
            details = details()
        )
}

data class ErrorResponse(
    val apiError: ApiError,
    val message: String,
    val details: Map<String, List<String>>? = null
)

enum class ApiError {
    RESOURCE_NOT_FOUND,
    BAD_REQUEST,
    PERSISTENCE_FAILED,
    CONFLICT
}