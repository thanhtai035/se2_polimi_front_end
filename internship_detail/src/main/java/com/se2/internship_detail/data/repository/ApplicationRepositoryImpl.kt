package com.se2.internship_detail.data.repository

import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.data.service.ApplicationService
import com.se2.internship_detail.domain.repository.ApplicationRepository


class ApplicationRepositoryImpl (
    private val apiService: ApplicationService
) : ApplicationRepository {
    override suspend fun sendApplication(applicationDto: ApplicationDto): Boolean {
        return try {
            apiService.sendApplication(applicationDto)
            true
        } catch (e: Exception) {
            false
        }
    }
}