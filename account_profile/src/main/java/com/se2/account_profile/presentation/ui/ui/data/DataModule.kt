package com.se2.account_profile.presentation.ui.ui.data

import com.se2.account_profile.presentation.ui.ui.data.repository.ProfileRepositoryImpl
import com.se2.account_profile.presentation.ui.ui.data.service.ProfileService
import com.se2.account_profile.presentation.ui.ui.domain.repository.ProfileRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {


    single<ProfileRepository> { ProfileRepositoryImpl(get()) }

    single { get<Retrofit>().create(ProfileService::class.java) }

}
