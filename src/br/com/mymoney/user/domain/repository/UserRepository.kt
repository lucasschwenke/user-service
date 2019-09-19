package br.com.mymoney.user.domain.repository

import br.com.mymoney.user.domain.model.User

interface UserRepository {

    fun getUser(userId: String): User?

}