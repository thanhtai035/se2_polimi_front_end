package com.se2.student_dashboard.data.dto

import androidx.compose.ui.input.pointer.PointerEventPass
import kotlinx.serialization.Serializable

@Serializable
data class QuestionnaireFormDto(
    val application: ShareApplicationDto,
    val name: String,
    val link: String,
    val passsed: Boolean
)

