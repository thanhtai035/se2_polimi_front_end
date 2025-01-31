package com.se2.students_and_companies.app.domain.repository

import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.domain.model.AuthenticationResponse

interface AuthenticationRepository {
    suspend fun getAuthenticationResponse(username: String, password: String) : User

    suspend fun registerStudent(user: RegisterStudentDto): Boolean
}