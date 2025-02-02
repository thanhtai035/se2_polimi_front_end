package com.se2.student_dashboard.data.repository

import Internship
import InternshipResponse
import android.util.Log
import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto
import com.se2.student_dashboard.data.service.ApplicationService
import com.se2.student_dashboard.data.service.InternshipService
import com.se2.student_dashboard.data.service.UserService
import com.se2.student_dashboard.domain.repository.ApplicationRepository
import com.se2.student_dashboard.domain.repository.InternshipRepository
import com.se2.student_dashboard.domain.repository.UserRepository

class ApplicationRepositoryImpl (
    private val apiService: ApplicationService
) : ApplicationRepository {
    override suspend fun postQuestionnaire(questionnaireFormDto: QuestionnaireFormDto): Boolean {
        return try {
            apiService.postQuestionnaire(questionnaireFormDto)
            true
        } catch (e: Exception) {false}
    }

    override suspend fun postInterview(interviewFormDto: InterviewFormDto): Boolean {
        return try {
            apiService.postInterview(interviewFormDto)
            true
        } catch (e: Exception) {false}    }

    override suspend fun updateStatus(applicationID: String, status: StatusUpdateDto): Boolean {
        return try {
            apiService.updateApplication(applicationID, status)
            true
        } catch (e: Exception) {
            false}
    }
}