package com.se2.students_and_companies.app.data.dto

import com.se2.students_and_companies.app.domain.model.AuthenticationResponse
import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class AuthenticationResponseDto(
    @SerialName("userID")
    val userID: String,
    @SerialName("success")
    val success: Boolean

)

public fun AuthenticationResponseDto.toEntity(): AuthenticationResponse {
    return AuthenticationResponse(
        userID = userID,
        success = success
    )
}