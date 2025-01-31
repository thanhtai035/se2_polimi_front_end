package com.se2.students_and_companies.app.domain.use_case

import com.se2.base.model.User
import com.se2.students_and_companies.app.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result

class GetAuthenticationResponseUseCase (
    private val repository: AuthenticationRepository
){
    operator fun invoke(username: String, password: String): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading<User>())
            val result = repository.getAuthenticationResponse(username,password)
            emit(Result.Success<User>(result))
        } catch(e: HttpException) {
            emit(Result.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Result.Error<User>("Couldn't reach server. Check your internet connection."))
        }
    }

}