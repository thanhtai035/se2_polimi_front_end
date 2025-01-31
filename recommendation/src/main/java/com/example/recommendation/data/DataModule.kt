package com.example.recommendation.data

import com.example.recommendation.data.repository.RecommendationRepositoryImpl
import com.example.recommendation.data.service.RecommendationService
import com.example.recommendation.domain.repository.RecommendationRepository
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dataModule = module {
    single<RecommendationRepository> { RecommendationRepositoryImpl(get()) }

    single { get<Retrofit>().create(RecommendationService::class.java) }

}
