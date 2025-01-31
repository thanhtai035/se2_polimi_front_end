package com.se2.students_and_companies.app.domain.use_case

import com.se2.base.model.User
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.base.model.Application
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import com.se2.students_and_companies.app.domain.repository.ApplicationRepository

class GetCompanyApplicationUseCase (
    private val repository: ApplicationRepository
){
    operator fun invoke(companyID: String): Flow<Result<List<Application>>> = flow {
        try {
            emit(Result.Loading<List<Application>>())
            val result = repository.getApplicationByCompany(companyID)
            emit(Result.Success<List<Application>>(result))
        } catch(e: HttpException) {
            emit(Result.Error<List<Application>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<List<Application>>("Couldn't reach server. Check your internet connection."))
        }
    }

}