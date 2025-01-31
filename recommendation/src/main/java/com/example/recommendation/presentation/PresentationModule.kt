package com.example.recommendation.presentation

import com.example.recommendation.presentation.viewmodel.RecommendationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::RecommendationViewModel)
}
