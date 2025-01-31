package com.se2.student_dashboard.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.base.model.User

import com.se2.student_dashboard.domain.repository.UserRepository

class GetUserUseCase (
    private val repository: UserRepository
){
    operator fun invoke(userID: String
    ): Flow<Result<User>> = flow {

        try {
            emit(Result.Loading<User>())
            val result = repository.getUser(userID)
            emit(Result.Success<User>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.message())
            emit(Result.Error<User>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<User>(e.toString()))
        }
    }
}