package com.example.recommendation.domain.repository

import Internship

interface RecommendationRepository {
    suspend fun getFirstRecommendation(userID: Int): List<Internship>

    suspend fun getSecondRecommendation(userID: Int): List<Internship>
}