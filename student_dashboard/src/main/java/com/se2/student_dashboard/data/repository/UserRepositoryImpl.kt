package com.se2.student_dashboard.data.repository

import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.UniBodyHolderDto
import com.se2.student_dashboard.data.service.UserService
import com.se2.student_dashboard.domain.repository.UserRepository

class UserRepositoryImpl (
    private val apiService: UserService
) : UserRepository {
    override suspend fun getStudents(uniName: String): List<User> {
        return  apiService.getStudents(UniBodyHolderDto(uniName)).map {
            item ->
            item.toEntity()
        }
    }
}