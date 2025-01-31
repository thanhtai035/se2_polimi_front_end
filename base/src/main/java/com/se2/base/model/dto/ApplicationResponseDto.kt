package com.se2.base.model.dto

import com.se2.base.model.Application
import com.se2.base.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationResponseDto(
    val data: List<ApplicationDto>,
    val hasNextPage: Boolean
) {
    fun toEntity(): List<Application> {
        return data.map { item ->
            Application(
                status = item.status,
                createdAt = item.createdAt,
                updatedAt = item.updatedAt,
                id = item.id,
                internship = item.internship?.toEntity(),
                motivationText = item.motivationText,
                student = item.student?.toEntity()
            )
        }
    }
}

@Serializable
data class ListApplicationDto(
    val applicationList: List<ApplicationDto>
) {
    fun toEntity(): List<Application> {
        return applicationList.map {item ->
            Application(
                status = item.status,
                createdAt = item.createdAt,
                updatedAt = item.updatedAt,
                id = item.id,
                internship = item.internship?.toEntity(),
                motivationText = item.motivationText,
                student = item.student?.toEntity()
            )
        }
    }
}

@kotlinx.serialization.Serializable
data class ApplicationDto(
    val status: String?,
    val student: UserDto?,
    val internship: InternshipDto?,
    val motivationText: String?,
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?
)

