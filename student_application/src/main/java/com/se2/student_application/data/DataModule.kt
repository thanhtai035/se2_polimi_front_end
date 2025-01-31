package com.se2.student_application.data

import com.se2.student_application.data.repository.ApplicationRepositoryImpl
import com.se2.student_application.data.service.ApplicationService
import com.se2.student_application.domain.repository.ApplicationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) }

    single { get<Retrofit>().create(ApplicationService::class.java) }

}
