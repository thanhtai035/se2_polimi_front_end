package com.se2.student_dashboard.data.repository

import Internship
import InternshipResponse
import android.util.Log
import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.data.service.InternshipService
import com.se2.student_dashboard.data.service.UserService
import com.se2.student_dashboard.domain.repository.InternshipRepository
import com.se2.student_dashboard.domain.repository.UserRepository

class InternshipRepositoryImpl (
    private val apiService: InternshipService
) : InternshipRepository {
    override suspend fun getAllInternship(): List<Internship> {
        return apiService.getAllInternship().toEntity()
    }

    override suspend fun postInternship(internshipFormDto: InternshipFormDto): Boolean {
        return try {
            apiService.postInternship(internshipFormDto)
            return true
        } catch (e: Exception) {
            Log.d("taitest", "Add Internship: "+ e.message.toString())
            return false
        }
    }
}