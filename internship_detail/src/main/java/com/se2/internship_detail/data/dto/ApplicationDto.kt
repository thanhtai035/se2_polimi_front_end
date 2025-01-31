package com.se2.internship_detail.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDto(
    val motivationText: String,
    val status: String,
    val internship: InternshipRefDto,
    val student: StudentRefDto
)

@Serializable
data class InternshipRefDto(
    val id: String?
)

@Serializable
data class StudentRefDto(
    val id: Int
)
