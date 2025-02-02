package com.se2.base.model.dto

import com.se2.base.model.Application
import com.se2.base.model.Interview
import com.se2.base.model.Questionnaire
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
                student = item.student?.toEntity(),
                questionnaires = item.questionnaires.map { i -> i.toModel()},
                interviews = item.interviews.map { i ->  i.toModel()}
            )
        }
    }
}

@Serializable
data class ApplicationDto(
    val status: String?,
    val student: UserDto?,
    val internship: InternshipDto?,
    val motivationText: String?,
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val questionnaires: List<QuestionnaireDto>,
    val interviews: List<InterviewDto>
)

@Serializable
data class QuestionnaireDto(
    val id: String?,
    val name: String? = null,
    val date: String? = null,
    val description: String? = null,
    val passed: Boolean?,
    val link: String? = null,
    val createdAt: String?,
    val updatedAt: String?,
) {
    fun toModel(): Questionnaire {
        return Questionnaire(
            id = id ?: "",
            name = name ?: "",
            date = date,
            description = description,
            passed = passed ?: false,
            link = link ?: "",
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: ""
        )
    }
}

@Serializable
data class InterviewDto(
    val id: String?,
    val title: String? = null,
    val date: String? = null,
    val description: String? = null,
    val link: String? = null,
    val passed: Boolean? = null,
    val location: String? = null,
    val createdAt: String?,
    val updatedAt: String?,
) {
    fun toModel(): Interview {
        return Interview(
            id = id ?: "",
            title = title ?: "",
            date = date,
            description = description,
            link = link ?: "",
            passed = passed ?: false,
            location = location ?: "",
            createdAt = createdAt ?: "",
            updatedAt = updatedAt ?: "",
        )
    }
}

