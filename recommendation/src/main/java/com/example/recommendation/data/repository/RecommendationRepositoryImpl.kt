package com.example.recommendation.data.repository

import Internship
import com.example.recommendation.data.service.RecommendationService
import com.example.recommendation.domain.repository.RecommendationRepository


class RecommendationRepositoryImpl (
    private val apiService: RecommendationService
) : RecommendationRepository {
    override suspend fun getFirstRecommendation(userID: Int): List<Internship> {
        return apiService.getFirstRecommendation(userID).map {
            it.toEntity()
        }
    }

    override suspend fun getSecondRecommendation(userID: Int): List<Internship> {
        return apiService.getSecondRecommendation(userID).map {
            it.toEntity()
        }    }

}