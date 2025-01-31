package com.se2.student_dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostInternshipResponseDto(
    val company: CompanyPostInternshipDto,
    val optionalSkills: String?,
    val requiredSkills: String,
    val endDate: String,
    val startDate: String,
    val duration: String,
    val salary: String,
    val type: String,
    val description: String,
    val title: String,
    val id: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class CompanyPostInternshipDto(
    val image: String? = null,
    val id: Int,
    val fullName: String,
    val role: RoleDto,
    val status: StatusDto,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val aboutYou: String? = null,
    val address: String,
    val university: String? = null,
    val personalEmail: String? = null,
    val attributes: String? = null
)

@Serializable
data class RoleDto(
    val id: Int
)

@Serializable
data class StatusDto(
    val id: Int
)
