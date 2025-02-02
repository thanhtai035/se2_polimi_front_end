package com.se2.student_dashboard.domain.repository

import com.se2.base.model.User


interface UserRepository {
    suspend fun getStudents(uniName: String) : List<User>

}