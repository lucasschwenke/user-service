package br.com.mymoney.user.application.web.request

data class UserRequest(val name: String,
                       val lastName: String,
                       val email: String,
                       val taxIdentifier: String)
