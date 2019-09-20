package br.com.mymoney.user.resource.mysql

import br.com.mymoney.user.domain.exception.PersistenceFailedException
import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.repository.UserRepository
import br.com.mymoney.user.domain.util.toJavaLocalDateTime
import br.com.mymoney.user.resource.persisntece.table.UserTable
import io.azam.ulidj.ULID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MySqlUserRepository: UserRepository {

    override fun getUser(userId: String): User? =
        transaction {
            try {
                UserTable.selectAll()
                    .andWhere { UserTable.id eq userId }
                    .firstOrNull()
                    ?.let { buildUser(it) }

            } catch (e: Exception) {
                throw PersistenceFailedException(e)
            }
        }

    override fun findUserBy(email: String?, taxIdentifier: String?): User? =
        transaction {
            try {
                val query = UserTable.selectAll()

                email?.let {
                    query.andWhere { UserTable.email eq email }
                }

                taxIdentifier?.let {
                    query.andWhere { UserTable.taxIdentifier eq taxIdentifier }
                }

                query.firstOrNull()?.let { buildUser(it) }

            } catch (e: Exception) {
                throw PersistenceFailedException(e)
            }
        }

    override fun insert(user: User): User =
        transaction {
            val ulid = ULID.random()

            try {
                UserTable.insert {
                    it[id] = ulid
                    it[name] = user.name
                    it[lastName] = user.lastName
                    it[email] = user.email
                    it[taxIdentifier] = user.taxIdentifier
                }

                user.copy(id = ulid)
            } catch (e: Exception) {
                throw PersistenceFailedException(e)
            }
        }

    override fun update(user: User): User =
        transaction {
            try {
                UserTable.update(where = { UserTable.id eq user.id!! }) {
                    it[name] = user.name
                    it[lastName] = user.lastName
                    it[email] = user.email
                }

                user
            } catch (e: Exception) {
                throw PersistenceFailedException(e)
            }
        }

    private fun buildUser(resultRow: ResultRow): User = resultRow.let {
        User(
            id = it[UserTable.id],
            name = it[UserTable.name],
            lastName = it[UserTable.lastName],
            email = it[UserTable.email],
            taxIdentifier = it[UserTable.taxIdentifier],
            createdAt = it[UserTable.createdAt].toJavaLocalDateTime(),
            updatedAt = it[UserTable.updatedAt]?.toJavaLocalDateTime()
        )
    }
}
