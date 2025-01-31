package com.se2.account_profile.presentation.ui.ui.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackDto(
    @SerialName("user")
    val user: FeedbackUserDto,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("rating")
    val rating: Int
)

@Serializable
data class FeedbackUserDto(
    @SerialName("id")
    val id: Int
)
