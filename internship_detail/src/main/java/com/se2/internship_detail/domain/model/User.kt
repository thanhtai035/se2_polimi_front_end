package com.se2.internship_detail.domain.model


@kotlinx.serialization.Serializable
data class User(
    val balance: Double,
    val debitCard: String?,
    val firstName: String,
    val lastName: String,
    val loaningCard: String?,
    val savingCard: String?,
    val username: String
)