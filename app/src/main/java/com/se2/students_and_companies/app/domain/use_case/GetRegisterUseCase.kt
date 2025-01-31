package com.se2.students_and_companies.app.domain.use_case

import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result

class GetRegisterUseCase (
    private val repository: AuthenticationRepository
){
    operator fun invoke(user: RegisterStudentDto): Flow<Result<Boolean>> = flow {
        try {
            emit(Result.Loading<Boolean>())
            val result = repository.registerStudent(user)
            emit(Result.Success<Boolean>(result))
        } catch(e: HttpException) {
            emit(Result.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<Boolean>("Couldn't reach server. Check your internet connection."))
        }
    }

}