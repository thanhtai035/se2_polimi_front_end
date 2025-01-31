package com.se2.students_and_companies.app.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StudentApplicationHolderDto (
    @SerialName("user")
    val user: UserID,
)

@Serializable
class UserID (
    @SerialName("id")
    val id: Int
)
