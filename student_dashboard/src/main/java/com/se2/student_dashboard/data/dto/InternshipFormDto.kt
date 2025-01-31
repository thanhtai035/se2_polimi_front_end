package com.se2.student_dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class InternshipFormDto(
    val company: CompanyDto,
    val title: String,
    val description: String,
    val requiredSkills: String,
    val optionalSkills: String?,
    val startDate: String,
    val endDate: String,
    val duration: String,
    val salary: String,
    val type: String
)

@Serializable
data class CompanyDto(
    val id: Int
)
