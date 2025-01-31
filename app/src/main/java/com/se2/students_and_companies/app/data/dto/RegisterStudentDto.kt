package com.se2.students_and_companies.app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterStudentDto(
    val email: String,
    val password: String,
    val fullName: String,
    val gender: String,
    val phoneNumber: String,
    val aboutYou: String,
    val address: String,
    val university: String,
    val personalEmail: String,
    val attributes: String,
    val role: Int, // 1 for admin, 2 for student, 3 for company, 4 for university
    @SerialName("cv_link")
    val cv: String
)
