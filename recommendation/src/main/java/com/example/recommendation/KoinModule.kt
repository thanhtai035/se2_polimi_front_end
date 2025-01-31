package com.example.recommendation

import com.example.recommendation.data.dataModule
import com.example.recommendation.domain.domainModule
import com.example.recommendation.presentation.presentationModule


val featureRecommendationModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
