package com.se2.base.model

import java.io.Serializable

data class User(
    val id: Int,
    val email: String?,
    val provider: String?,
    val fullName: String,
    val role: Int,
    val status: Int,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?,
    val gender: String?,
    val phoneNumber: String?,
    val aboutYou: String?,
    val address: String,
    val university: String?,
    val personalEmail: String?,
    val attributes: String?,
    val cv_link: String?
) : Serializable
