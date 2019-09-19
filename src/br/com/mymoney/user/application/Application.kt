package br.com.mymoney.user.application

import br.com.mymoney.user.application.web.controller.UserController
import br.com.mymoney.user.common.koin.DatabaseFactory
import br.com.mymoney.user.common.koin.applicationModule
import br.com.mymoney.user.domain.exception.ApiException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Koin) {
        modules(applicationModule)
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)

            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    install(StatusPages) {
        exception<ApiException> { cause ->
            log.error(
                "Error while processing request: ${this.context.request.httpMethod.value} - " +
                        "${this.context.request.uri}: ${cause.userResponseMessage()}. Status: ${cause.httpStatus().value}"
            )

            call.respond(
                status = cause.httpStatus(),
                message = cause.createErrorResponse()
            )
        }
    }

    DatabaseFactory.init()

    val userController: UserController by inject()

    routing {
        route("users") {
            post {
                call.respond(userController.insertUser(this.call))
            }

            get("/{id}") {
                call.respond(userController.getUser(this.call))
            }
        }
    }
}

