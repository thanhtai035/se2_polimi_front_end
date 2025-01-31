package com.se2.internship_detail.domain.repository

import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.domain.model.User

interface ApplicationRepository {
    suspend fun sendApplication(applicationDto: ApplicationDto) : Boolean

}