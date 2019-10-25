package br.com.mymoney.user.service

import br.com.mymoney.user.domain.exception.BadRequestException
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

    companion object {
        const val NAME = "Piccolo"
        const val LAST_NAME = "Namekusei"
        const val EMAIL = "gohan.sayadin@mailinator.com"
        const val TAX_IDENTIFIER = "85425748000"
    }

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
    fun `should create a new user`() {
        val newUser = generateNewUser()

        every { userRepository.findUserBy(email = newUser.email) } returns null
        every { userRepository.findUserBy(taxIdentifier = newUser.taxIdentifier) } returns null
        every { userRepository.insert(newUser) } returns newUser.copy(
            id = ULID.random(),
            createdAt = LocalDateTime.now()
        )

        val createdUser = userService.insertUser(newUser)

        createdUser.apply {
            assertNotNull(this.id)

            assertNotNull(this.name)
            assertThat(this.name).isEqualTo(NAME)

            assertNotNull(this.lastName)
            assertThat(this.lastName).isEqualTo(LAST_NAME)

            assertNotNull(this.email)
            assertThat(this.email).isEqualTo(EMAIL)

            assertNotNull(this.taxIdentifier)
            assertThat(this.taxIdentifier).isEqualTo(TAX_IDENTIFIER)

            assertNotNull(this.createdAt)
        }
    }

    @Test
    fun `should thrown an exception when there is a user with same email`() {
        val newUser = generateNewUser()

        every { userRepository.findUserBy(email = newUser.email) } returns validUser

        assertThatThrownBy { userService.insertUser(newUser) }
            .isExactlyInstanceOf(ConflictException::class.java)
            .hasMessage("The email ${newUser.email} has already been registered.")
    }

    @Test
    fun `should thrown an exception when there is a user with same tax identifier`() {
        val newUser = generateNewUser()

        every { userRepository.findUserBy(email = newUser.email) } returns null
        every { userRepository.findUserBy(taxIdentifier = newUser.taxIdentifier) } returns validUser

        assertThatThrownBy { userService.insertUser(newUser) }
            .isExactlyInstanceOf(ConflictException::class.java)
            .hasMessage("The tax identifier ${newUser.taxIdentifier} has already been registered.")
    }

    @Test
    fun `should update a exists user`() {
        val updateUser = generateUpdateUser()

        val mergedUser = updateUser.copy(id = validUser.id)

        every { userRepository.findUserBy(taxIdentifier = updateUser.taxIdentifier) } returns validUser
        every { userRepository.findUserBy(email = updateUser.email) } returns null
        every { userRepository.update(mergedUser) } returns mergedUser.copy(
            updatedAt = LocalDateTime.now()
        )

        val userReturned = userService.update(updateUser)

        userReturned.apply {
            assertNotNull(this.id)
            assertThat(this.id).isEqualTo(validUser.id)

            assertNotNull(this.name)
            assertThat(this.name).isEqualTo(NAME)

            assertNotNull(this.lastName)
            assertThat(this.lastName).isEqualTo(LAST_NAME)

            assertNotNull(this.email)
            assertThat(this.email).isEqualTo(EMAIL)

            assertNotNull(this.taxIdentifier)
            assertThat(this.taxIdentifier).isEqualTo(validUser.taxIdentifier)
        }
    }

    @Test
    fun `should thrown an exception when not found user with tax identifier`() {
        val updateUser = generateUpdateUser()

        every { userRepository.findUserBy(taxIdentifier = updateUser.taxIdentifier) } returns null

        assertThatThrownBy { userService.update(updateUser) }
            .isExactlyInstanceOf(BadRequestException::class.java)
            .hasMessage("User with tax identifier ${updateUser.taxIdentifier} not found.")
    }

    @Test
    fun `should thrown an exception when there is user with same email`() {
        val updateUser = generateUpdateUser()

        every { userRepository.findUserBy(taxIdentifier = updateUser.taxIdentifier) } returns validUser
        every { userRepository.findUserBy(email = updateUser.email) } returns updateUser.copy()

        assertThatThrownBy { userService.update(updateUser) }
            .isExactlyInstanceOf(ConflictException::class.java)
            .hasMessage("The email ${updateUser.email} has already been registered.")
    }

    private fun generateUpdateUser() =
        User(
            name = NAME,
            lastName = LAST_NAME,
            email = EMAIL,
            taxIdentifier = validUser.taxIdentifier
        )


    private fun generateNewUser() =
        User(
            name = NAME,
            lastName = LAST_NAME,
            email = EMAIL,
            taxIdentifier = TAX_IDENTIFIER
        )

}