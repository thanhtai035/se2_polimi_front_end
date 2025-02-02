package com.se2.student_dashboard.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.base.model.User

import com.se2.student_dashboard.domain.repository.UserRepository

class GetStudentListUseCase (
    private val repository: UserRepository
){
    operator fun invoke(uniName: String
    ): Flow<Result<List<User>>> = flow {

        try {
            emit(Result.Loading<List<User>>())
            val result = repository.getStudents(uniName)
            emit(Result.Success<List<User>>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.message())
            emit(Result.Error<List<User>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<List<User>>(e.toString()))
        }
    }
}