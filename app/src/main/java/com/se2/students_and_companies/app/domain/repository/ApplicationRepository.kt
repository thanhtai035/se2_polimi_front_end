package com.se2.students_and_companies.app.domain.repository

import com.se2.base.model.Application
import com.se2.base.model.User
import com.se2.base.model.dto.UserDto
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import com.se2.students_and_companies.app.domain.model.AuthenticationResponse

interface ApplicationRepository {
    suspend fun getApplicationByStudent(studentApplicationHolderDto: StudentApplicationHolderDto): List<Application>

    suspend fun getApplicationByCompany(id: String): List<Application>
}