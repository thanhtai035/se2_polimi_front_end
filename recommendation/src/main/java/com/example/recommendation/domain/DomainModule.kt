package com.example.recommendation.domain

import com.example.recommendation.domain.use_case.GetFirstRecommendationUseCase
import com.example.recommendation.domain.use_case.GetSecondRecommendationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetFirstRecommendationUseCase)
    singleOf(::GetSecondRecommendationUseCase)


}
