package com.se2.student_dashboard.domain.repository

import Internship
import InternshipResponse
import com.se2.base.model.User
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto


interface ApplicationRepository {
    suspend fun postQuestionnaire(questionnaireFormDto: QuestionnaireFormDto) : Boolean

    suspend fun postInterview(interviewFormDto: InterviewFormDto): Boolean

    suspend fun updateStatus(applicationID: String, status: StatusUpdateDto): Boolean
}