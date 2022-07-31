package com.impact.plugins

import com.impact.feature.expense.data.repository.ExpenseRepositoryImpl
import com.impact.feature.expense.domain.repository.ExpenseRepository
import com.impact.feature.funds.data.repository.FundsRepositoryImpl
import com.impact.feature.funds.domain.repository.FundsRepository
import com.impact.feature.funds.domain.services.FundService
import com.impact.feature.user.data.repository.UserRepositoryImpl
import com.impact.feature.user.domain.repository.UserRepository
import com.impact.feature.user.domain.services.UserService
import com.impact.feature.verification.repository.AuthenticationRepository
import com.impact.feature.verification.repository.AuthenticationRepositoryImpl
import com.impact.utility.TokenManagerService
import com.impact.utility.TokenManagerServiceImpl
import io.ktor.server.application.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger


fun Application.configureDependencyInjection(){
    val modules = module {
        //Middleware dependencies
        singleOf(::TokenManagerServiceImpl) { bind<TokenManagerService>() }
        singleOf(::AuthenticationRepositoryImpl){ bind<AuthenticationRepository>() }

        //Funds Dependency
        singleOf(::FundsRepositoryImpl){ bind<FundsRepository>() }
            .onClose {

            }
        singleOf(::FundService)

        //User Dependency
        singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
        singleOf(::UserService)

        //Expense Repository
        singleOf(::ExpenseRepositoryImpl){bind<ExpenseRepository>()}
    }
    install(Koin) {
        SLF4JLogger()
        modules(modules)
    }
}

