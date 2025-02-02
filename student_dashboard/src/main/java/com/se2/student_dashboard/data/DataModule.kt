package com.se2.student_dashboard.data

import com.se2.student_dashboard.data.repository.ApplicationRepositoryImpl
import com.se2.student_dashboard.data.repository.InternshipRepositoryImpl
import com.se2.student_dashboard.data.repository.UserRepositoryImpl
import com.se2.student_dashboard.data.service.ApplicationService
import com.se2.student_dashboard.data.service.InternshipService
import com.se2.student_dashboard.data.service.UserService
import com.se2.student_dashboard.domain.repository.ApplicationRepository
import com.se2.student_dashboard.domain.repository.InternshipRepository
import com.se2.student_dashboard.domain.repository.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<InternshipRepository> { InternshipRepositoryImpl(get()) }

    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) }

    single { get<Retrofit>().create(UserService::class.java) }

    single { get<Retrofit>().create(InternshipService::class.java) }

    single { get<Retrofit>().create(ApplicationService::class.java) }

}
