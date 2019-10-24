package br.com.mymoney.br.com.mymoney.user.service

import br.com.mymoney.user.domain.exception.ConflictException
import br.com.mymoney.user.domain.exception.ResourceNotFoundException
import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.repository.UserRepository
import br.com.mymoney.user.domain.service.UserService
import io.azam.ulidj.ULID
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

import java.time.LocalDateTime
import kotlin.test.assertNotNull

class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    private val validUser = User(
        id = ULID.random(),
        name = "Goku",
        lastName = "kakaroto",
        email = "goku.kakaroto@mailinator.com",
        taxIdentifier = "15256168037",
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    @Test
    fun `should return user by id`() {
        every { userRepository.getUser("1") } returns validUser

        val userReturned = userService.getUser("1")

        userReturned.apply {
            assertNotNull(this.id)
            assertThat(this.id).isEqualTo(validUser.id)

            assertNotNull(this.name)
            assertThat(this.name).isEqualTo(validUser.name)

            assertNotNull(this.lastName)
            assertThat(this.lastName).isEqualTo(validUser.lastName)

            assertNotNull(this.email)
            assertThat(this.email).isEqualTo(validUser.email)

            assertNotNull(this.taxIdentifier)
            assertThat(this.taxIdentifier).isEqualTo(validUser.taxIdentifier)

            assertNotNull(this.createdAt)
            assertThat(this.createdAt).isEqualTo(validUser.createdAt)

            assertNotNull(this.updatedAt)
            assertThat(this.updatedAt).isEqualTo(validUser.updatedAt)
        }
    }

    @Test
    fun `should thrown an exception than there is not a user`() {
        every { userRepository.getUser("1") } returns null

        assertThatThrownBy { userService.getUser("1") }
            .isExactlyInstanceOf(ResourceNotFoundException::class.java)
            .hasMessage("The user 1 not found")
    }

    @Test
    fun `test`() {
        val email = "gohan.sayadin@mailinator.com"
        val taxIdentifier = "85425748000"

        val newUser = User(
            name = "Piccolo",
            lastName = "Namekusei",
            email = email,
            taxIdentifier = taxIdentifier
        )

        every { userRepository.findUserBy(email = email) } returns null
        every { userRepository.findUserBy(taxIdentifier = taxIdentifier) } returns null
        every { userRepository.insert(newUser) } returns newUser.copy(
            id = ULID.random(),
            createdAt = LocalDateTime.now()
        )

        val createdUser = userService.insertUser(newUser)

        createdUser.apply {
            assertNotNull(this.id)

            assertNotNull(this.name)
            assertThat(this.name).isEqualTo("Piccolo")

            assertNotNull(this.lastName)
            assertThat(this.lastName).isEqualTo("Namekusei")

            assertNotNull(this.email)
            assertThat(this.email).isEqualTo(email)

            assertNotNull(this.taxIdentifier)
            assertThat(this.taxIdentifier).isEqualTo(taxIdentifier)

            assertNotNull(this.createdAt)
        }
    }

    @Test
    fun `test2`() {
        val email = "gohan.sayadin@mailinator.com"

        val newUser = User(
            name = "Piccolo",
            lastName = "Namekusei",
            email = email,
            taxIdentifier = "85425748000"
        )

        every { userRepository.findUserBy(email = email) } returns validUser

        assertThatThrownBy { userService.insertUser(newUser) }
            .isExactlyInstanceOf(ConflictException::class.java)
            .hasMessage("The email $email has already been registered.")
    }

    @Test
    fun `test3`() {
        val taxIdentifier = "85425748000"
        val email = "gohan.sayadin@mailinator.com"

        val newUser = User(
            name = "Piccolo",
            lastName = "Namekusei",
            email = email,
            taxIdentifier = taxIdentifier
        )

        every { userRepository.findUserBy(email = email) } returns null
        every { userRepository.findUserBy(taxIdentifier = taxIdentifier) } returns validUser

        assertThatThrownBy { userService.insertUser(newUser) }
            .isExactlyInstanceOf(ConflictException::class.java)
            .hasMessage("The tax identifier $taxIdentifier has already been registered.")
    }


}