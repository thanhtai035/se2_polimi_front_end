package com.se2.students_and_companies.app.data.repository

import android.util.Log
import com.se2.base.model.Application
import com.se2.base.model.User
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import com.se2.students_and_companies.app.data.service.ApplicationService
import com.se2.students_and_companies.app.data.service.AuthenticationService
import com.se2.students_and_companies.app.domain.repository.ApplicationRepository
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository

class ApplicationRepositoryImpl (
    private val applicationService: ApplicationService
): ApplicationRepository {
    override suspend fun getApplicationByStudent(studentApplicationHolderDto: StudentApplicationHolderDto): List<Application> {
        try {
            return applicationService.getStudentApplication().toEntity()
        } catch (e: Exception) {
            Log.d("taitest", e.message.toString())
            return applicationService.getStudentApplication().toEntity()
        }

    }

    override suspend fun getApplicationByCompany(id: String): List<Application> {
        return applicationService.getCompanyApplication(id).map {item ->
            Application(
                status = item.status,
                createdAt = item.createdAt,
                updatedAt = item.updatedAt,
                id = item.id,
                internship = item.internship?.toEntity(),
                motivationText = item.motivationText,
                student = item.student?.toEntity()
            )
        }
    }

}