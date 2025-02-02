package com.se2.student_dashboard.data.dto

import androidx.compose.ui.input.pointer.PointerEventPass
import kotlinx.serialization.Serializable

@Serializable
data class InterviewFormDto(
    val application: ShareApplicationDto,
    val title: String,
    val link: String,
    val location: String,
    val passed: Boolean
)

