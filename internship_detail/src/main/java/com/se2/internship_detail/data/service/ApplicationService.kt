package com.se2.internship_detail.data.service

import com.se2.internship_detail.data.dto.ApplicationDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface ApplicationService {

    @POST("/api/v1/applications")
    suspend fun sendApplication(@Body applicationDto: ApplicationDto)
}

