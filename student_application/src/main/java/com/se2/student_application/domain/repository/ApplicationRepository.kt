package com.se2.student_application.domain.repository

import Internship

interface ApplicationRepository {
    suspend fun studentUpdateApplication(applicationID: String, status: String): Boolean

}