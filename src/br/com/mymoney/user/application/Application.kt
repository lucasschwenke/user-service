package br.com.mymoney.user.application

import br.com.mymoney.user.application.web.controller.UserController
import br.com.mymoney.user.common.koin.DatabaseFactory
import br.com.mymoney.user.common.koin.applicationModule
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
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
        }
    }

    DatabaseFactory.init()

    val userController: UserController by inject()

    routing {
        route("users") {
            get("/{id}") {
                call.respond(userController.getUser(this.call))
            }
        }
    }
}

