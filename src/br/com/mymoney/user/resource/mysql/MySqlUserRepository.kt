package br.com.mymoney.user.resource.mysql

import br.com.mymoney.user.domain.model.User
import br.com.mymoney.user.domain.repository.UserRepository
import br.com.mymoney.user.domain.utils.toJavaLocalDateTime
import br.com.mymoney.user.resource.persisntece.table.UserTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MySqlUserRepository: UserRepository {

    override fun getUser(userId: String): User? =
        transaction {
            UserTable.selectAll()
                .andWhere { UserTable.id eq userId }
                .firstOrNull()
                ?.let {
                    User(
                        id = it[UserTable.id],
                        name = it[UserTable.name],
                        lastName = it[UserTable.lastName],
                        email = it[UserTable.email],
                        taxIdentifier = it[UserTable.taxIdentifier],
                        createdAt = it[UserTable.createdAt].toJavaLocalDateTime(),
                        updatedAt = it[UserTable.updatedAt].toJavaLocalDateTime()
                    )
                }
        }
}
