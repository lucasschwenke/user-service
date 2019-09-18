package br.com.mymoney.user.resource.persisntece.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object User : Table() {
    val id: Column<String> = varchar("id", 255).primaryKey()
    val name: Column<String> = varchar("name", 100)
    val lastName: Column<String> = varchar("last_name", 100)
    val email: Column<String> = varchar("email", 100)
    val taxIdentifier: Column<String> = varchar("tax_identifier", 100)
    val createdAt: Column<DateTime> = datetime("created_at")
    val updatedAt: Column<DateTime> = datetime("updated_at")
}