package com.se2.students_and_companies.app.data.repository

import android.util.Log
import com.se2.base.model.User
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.data.service.AuthenticationService
import com.se2.students_and_companies.app.data.service.JsonHolder
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl (
    private val authenticationService: AuthenticationService
): AuthenticationRepository {
    override suspend fun getAuthenticationResponse(username: String, password: String): User {
        return authenticationService.login(JsonHolder(username, password)).user.toEntity()
    }

    override suspend fun registerStudent(user: RegisterStudentDto): Boolean {
        return try {
            authenticationService.register(user)
            true
        } catch (e: Exception) {
            Log.d("taitest", e.message.toString())
            false}
    }
}