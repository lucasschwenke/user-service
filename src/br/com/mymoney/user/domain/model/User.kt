package br.com.mymoney.user.domain.model

import java.time.LocalDateTime

data class User(val id: String,
                val name: String,
                val lastName: String,
                val email: String,
                val taxIdentifier: String,
                val createdAt: LocalDateTime = LocalDateTime.now(),
                val updatedAt: LocalDateTime = LocalDateTime.now())
