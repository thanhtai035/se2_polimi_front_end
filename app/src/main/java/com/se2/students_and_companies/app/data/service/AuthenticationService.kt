package com.se2.students_and_companies.app.data.service

import com.se2.base.model.dto.UserResponseDto
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/api/v1/auth/email/login")
    suspend fun login(@Body jsonHolder: JsonHolder): UserResponseDto

    @POST("/api/v1/auth/email/register")
    suspend fun register(@Body user: RegisterStudentDto)
}