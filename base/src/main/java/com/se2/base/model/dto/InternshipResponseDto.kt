package com.se2.base.model.dto

import Company
import Internship
import InternshipStatus
import Role
import androidx.annotation.Nullable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// DTO for InternshipResponse
@Serializable
data class InternshipResponseDto(
    val data: List<InternshipDto>,
    val hasNextPage: Boolean
)  {
    fun toEntity(): List<Internship> {
        return data.map { internshipDto ->
            Internship(
                company = internshipDto.company.toEntity(),
                optionalSkills = internshipDto.optionalSkills,
                requiredSkills = internshipDto.requiredSkills,
                endDate = internshipDto.endDate,
                startDate = internshipDto.startDate,
                duration = internshipDto.duration,
                salary = internshipDto.salary,
                type = internshipDto.type,
                description = internshipDto.description,
                title = internshipDto.title,
                id = internshipDto.id,
                createdAt = internshipDto.createdAt,
                updatedAt = internshipDto.updatedAt
            )
        }
    }
}

// DTO for Internship
@Serializable
data class InternshipDto(
    val company: CompanyDto,
    val optionalSkills: String? = null,
    val requiredSkills: String? = null,
    val endDate: String?,
    val startDate: String?,
    val duration: String?,
    val salary: String?,
    val type: String?,
    val description: String?,
    val title: String?,
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val matchPercentage: Int? = null
) {
    fun toEntity(): Internship {
        return Internship(
            company = company.toEntity(),
            optionalSkills = optionalSkills,
            requiredSkills = requiredSkills,
            endDate = endDate,
            startDate, duration, salary, type, description, title, id, createdAt, updatedAt, matchPercentage
        )
    }
}

// DTO for Company
@Serializable
data class CompanyDto(
    val image: String? = null,
    val id: Int,
    val fullName: String,
    val role: RoleInternshipDto,
    val status: InternshipStatusDto,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String? = null,
    val gender: String? = null,
    val phoneNumber: String? = null,
    val aboutYou: String? = null,
    val address: String,
    val university: String? = null,
    val personalEmail: String? = null,
    val attributes: String? = null
) {
    fun toEntity(): Company {
        return Company(
            image = image,
            id = id,
            fullName = fullName,
            role = role.toEntity(),
            status = status.toEntity(),
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
            gender = gender,
            phoneNumber = phoneNumber,
            aboutYou = aboutYou,
            address = address,
            university = university,
            personalEmail = personalEmail,
            attributes = attributes
        )
    }
}

// DTO for Role
@Serializable
data class RoleInternshipDto(
    val id: Int,
    val name: String,
    @SerialName("__entity")
    val entity: String
) {
    fun toEntity(): Role {
        return Role(
            id = id,
            name = name,
            entity = entity
        )
    }
}

// DTO for InternshipStatus
@Serializable
data class InternshipStatusDto(
    val id: Int,
    val name: String,
    @SerialName("__entity")
    val entity: String
) {
    fun toEntity(): InternshipStatus {
        return InternshipStatus(
            id = id,
            name = name,
            entity = entity
        )
    }
}
