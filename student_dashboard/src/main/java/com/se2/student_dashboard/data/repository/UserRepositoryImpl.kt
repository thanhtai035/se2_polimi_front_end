package com.se2.student_dashboard.data.repository

import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.service.UserService
import com.se2.student_dashboard.domain.repository.UserRepository

class UserRepositoryImpl (
    private val apiService: UserService
) : UserRepository {
    override suspend fun getUser(
        userID: String,
    ): User {
        val user = apiService.getUser(userID).toEntity()
        return user
    }
}