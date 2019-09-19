package br.com.mymoney.user.domain.service

import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun getUser(userId: String): User {
        return userRepository.getUser(userId) ?: throw RuntimeException()
    }
}