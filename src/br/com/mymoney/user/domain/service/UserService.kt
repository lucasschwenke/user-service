package br.com.mymoney.user.domain.service

import br.com.mymoney.user.domain.exception.BadRequestException
import br.com.mymoney.user.domain.exception.ConflictException
import br.com.mymoney.user.domain.exception.ResourceNotFoundException
import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.repository.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun getUser(userId: String) = userRepository.getUser(userId)
        ?: throw ResourceNotFoundException("The user $userId not found")

    fun insertUser(user: User): User {
        checkDuplicateEmail(user.email)

        userRepository.findUserBy(taxIdentifier = user.taxIdentifier)?.run {
            throw ConflictException("The tax identifier $taxIdentifier has already been registered.")
        }

        return userRepository.insert(user)
    }

    fun update(user: User): User {
        val insertedUser = userRepository.findUserBy(taxIdentifier = user.taxIdentifier)
            ?: throw BadRequestException("User with tax identifier ${user.taxIdentifier} not found.")

        checkDuplicateEmail(user.email)

        val userUpdate = user.copy(
            id = insertedUser.id,
            name = user.name,
            lastName = user.lastName,
            email = user.email,
            taxIdentifier = insertedUser.taxIdentifier
        )

        return userRepository.update(userUpdate)
    }

    private fun checkDuplicateEmail(email: String) = userRepository.findUserBy(email = email)
        ?.run { throw ConflictException("The email $email has already been registered.") }
}