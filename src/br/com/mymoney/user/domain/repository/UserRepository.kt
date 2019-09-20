package br.com.mymoney.user.domain.repository

import br.com.mymoney.user.domain.model.User

interface UserRepository {

    fun getUser(userId: String): User?

    fun findUserBy(email: String? = null, taxIdentifier: String? = null): User?

    fun insert(user: User): User

    fun update(user: User): User

}