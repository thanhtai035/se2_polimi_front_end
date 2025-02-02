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
    val updatedAt: String?,
    val interviews : List<Interview>,
    val questionnaires: List<Questionnaire>
) : Serializable

data class Questionnaire(
    val id: String,
    val name: String,
    val date: String?,
    val description: String?,
    val passed: Boolean,
    val link: String,
    val createdAt: String,
    val updatedAt: String
): Serializable

data class Interview(
    val id: String,
    val title: String,
    val date: String?,
    val description: String?,
    val link: String,
    val passed: Boolean,
    val location: String,
    val createdAt: String,
    val updatedAt: String
): Serializable






