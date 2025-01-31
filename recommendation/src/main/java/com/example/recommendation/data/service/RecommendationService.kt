package com.example.recommendation.data.service

import com.se2.base.model.dto.InternshipDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecommendationService {

    @GET("/api/v1/recommendations/generate/{userID}")
    suspend fun getFirstRecommendation(@Path("userID") userID: Int): List<InternshipDto>

    @GET("/api/v1/recommendations/generate-optional/{userID}")
    suspend fun getSecondRecommendation(@Path("userID") userID: Int): List<InternshipDto>
}

