package br.com.mymoney.user.common.koin

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.HoconApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()

    fun init() {
        Database.connect(hikariConfig())

        val flyway = Flyway.configure()
            .dataSource(dbUrl, dbUser, dbPassword)
            .load()

        flyway.migrate()
    }

    private fun hikariConfig(): HikariDataSource {
        val config = HikariConfig()

        config.apply {
            this.driverClassName = "com.mysql.jdbc.Driver"
            this.jdbcUrl = dbUrl
            this.username = dbUser
            this.password = dbPassword
            this.maximumPoolSize = 3
            this.isAutoCommit = false
        }

        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}
