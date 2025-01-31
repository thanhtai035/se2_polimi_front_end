package com.se2.base.model.dto

import com.se2.base.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val refreshToken: String,
    val token: String,
    val tokenExpires: Long,
    val user: UserDto
)
