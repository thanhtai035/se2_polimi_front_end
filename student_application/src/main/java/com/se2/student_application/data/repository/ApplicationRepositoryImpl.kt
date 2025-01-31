package com.se2.student_application.data.repository

import Internship
import com.se2.base.model.Application
import com.se2.student_application.data.dto.UpdateApplicationBodyDto
import com.se2.student_application.data.service.ApplicationService
import com.se2.student_application.domain.repository.ApplicationRepository


class ApplicationRepositoryImpl (
    private val apiService: ApplicationService
) : ApplicationRepository {
    override suspend fun studentUpdateApplication(applicationID: String, status: String): Boolean {
        return try {
            apiService.updateApplication(applicationID, UpdateApplicationBodyDto(status))
            true
        } catch (e: Exception) {
            false
        }
    }
}