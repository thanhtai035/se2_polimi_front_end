package com.se2.students_and_companies.app.data

import com.se2.students_and_companies.app.data.repository.ApplicationRepositoryImpl
import com.se2.students_and_companies.app.data.repository.AuthenticationRepositoryImpl
import com.se2.students_and_companies.app.data.service.ApplicationService
import com.se2.students_and_companies.app.data.service.AuthenticationService
import com.se2.students_and_companies.app.domain.repository.ApplicationRepository
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) }

    single { get<Retrofit>().create(AuthenticationService::class.java) }

    single { get<Retrofit>().create(ApplicationService::class.java) }

}
