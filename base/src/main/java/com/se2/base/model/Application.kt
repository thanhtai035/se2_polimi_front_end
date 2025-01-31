package com.se2.base.model

import Internship
import java.io.Serializable

data class Application(
    val status: String?,
    val student: User?,
    val internship: Internship?,
    val motivationText: String?,
    val id: String?,
    val createdAt: String?,
    val updatedAt: String?
) : Serializable





