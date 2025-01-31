package com.se2.account_profile.presentation.ui.ui.data.dto

import com.se2.base.model.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackResponseDto(
    val user: UserDto,
    val rating: Int,
    val description: String,
    val title: String,
    val id: String,
    val createdAt: String,
    val updatedAt: String
)
