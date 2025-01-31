package com.se2.student_application.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateApplicationBodyDto (
    val status: String
)