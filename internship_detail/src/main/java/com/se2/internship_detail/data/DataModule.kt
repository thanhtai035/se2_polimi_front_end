package com.se2.internship_detail.data

import com.se2.internship_detail.data.repository.ApplicationRepositoryImpl
import com.se2.internship_detail.data.service.ApplicationService
import com.se2.internship_detail.domain.repository.ApplicationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {


    single<ApplicationRepository> { ApplicationRepositoryImpl(get()) }

    single { get<Retrofit>().create(ApplicationService::class.java) }

}
