package com.se2.student_dashboard.data.service

import InternshipResponse
import com.se2.base.model.dto.InternshipResponseDto
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface InternshipService {

    @GET("/api/v1/internships?page=1&limit=500")
    suspend fun getAllInternship() : InternshipResponseDto

    @POST("/api/v1/internships")
    suspend fun postInternship(@Body internshipFormDto: InternshipFormDto)
}

