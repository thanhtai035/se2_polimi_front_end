package com.se2.student_dashboard.data.service

import InternshipResponse
import com.se2.base.model.dto.InternshipResponseDto
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path


interface ApplicationService {

    @POST("/api/v1/questionnaires")
    suspend fun postQuestionnaire(@Body questionnaireFormDto: QuestionnaireFormDto)

    @POST("/api/v1/interviews")
    suspend fun postInterview(@Body interviewFormDto: InterviewFormDto)

    @PATCH("/api/v1/applications/{applicationID}")
    suspend fun updateApplication(@Path("applicationID") applicationID: String, @Body newStatus: StatusUpdateDto)
}

