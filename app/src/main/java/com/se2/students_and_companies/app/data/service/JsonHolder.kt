package com.se2.students_and_companies.app.data.service

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class JsonHolder (
    @SerialName("email")
    val username: String,

    @SerialName("password")
    val password: String
)
