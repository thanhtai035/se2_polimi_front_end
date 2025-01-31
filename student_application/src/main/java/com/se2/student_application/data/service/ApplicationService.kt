package com.se2.student_application.data.service

import com.se2.base.model.dto.InternshipDto
import com.se2.student_application.data.dto.UpdateApplicationBodyDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ApplicationService {
    @PATCH("/api/v1/applications/{applicationID}")
    suspend fun updateApplication(@Path("applicationID") applicationID: String, @Body updateApplicationBodyDto: UpdateApplicationBodyDto): Boolean

}

