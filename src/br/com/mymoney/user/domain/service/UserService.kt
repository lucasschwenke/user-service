package br.com.mymoney.user.domain.service

import br.com.mymoney.user.domain.exception.ResourceNotFoundException
import br.com.mymoney.user.domain.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun getUser(userId: String) = userRepository.getUser(userId)
        ?: throw ResourceNotFoundException("The user $userId not found")

}