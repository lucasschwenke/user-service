package br.com.mymoney.user.common.koin

import br.com.mymoney.user.domain.repository.UserRepository
import br.com.mymoney.user.domain.service.UserService
import br.com.mymoney.user.resource.mysql.MySqlUserRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule: Module = module {
    single { UserService() }

    single<UserRepository> { MySqlUserRepository() }
}