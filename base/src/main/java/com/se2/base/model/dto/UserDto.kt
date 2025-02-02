package com.se2.base.model.dto

import androidx.annotation.Nullable
import com.se2.base.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    @Nullable
    val email: String? = null,
    @Nullable
    val provider: String? = null,
    val fullName: String,
    val role: RoleDto,
    val status: StatusDto,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?,
    val gender: String?,
    val phoneNumber: String?,
    val aboutYou: String?,
    val address: String? = null,
    val university: String?,
    val personalEmail: String?,
    val attributes: String?,
    @Nullable
    val cv_link: String? = null
) {
    // Function to convert UserDto to User entity
    fun toEntity(): User {
        return User(
            id = this.id,
            email = this.email,
            provider = this.provider,
            fullName = this.fullName,
            role = this.role.id,
            status = this.status.id,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deletedAt = this.deletedAt,
            gender = this.gender,
            phoneNumber = this.phoneNumber,
            aboutYou = this.aboutYou,
            address = this.address,
            university = this.university,
            personalEmail = this.personalEmail,
            attributes = this.attributes,
            cv_link = this.cv_link
        )
    }
}

// RoleDto class for the DTO representation
@Serializable
data class RoleDto(val id: Int, val name: String? = null)

// StatusDto class for the DTO representation
@Serializable
data class StatusDto(val id: Int, val name: String? = null)
