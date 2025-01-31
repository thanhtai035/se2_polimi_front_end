package com.se2.students_and_companies.app.data.service

import com.se2.base.model.dto.ApplicationDto
import com.se2.base.model.dto.ApplicationResponseDto
import com.se2.base.model.dto.ListApplicationDto
import com.se2.base.model.dto.UserResponseDto
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApplicationService {
    @GET("/api/v1/applications?page=1&limit=500")
    suspend fun getStudentApplication(): ApplicationResponseDto

    @GET("/api/v1/applications/company/{companyID}")
    suspend fun getCompanyApplication(@Path("companyID") companyID: String): List<ApplicationDto>
}