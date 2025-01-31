package com.se2.student_application.domain.use_case

import Internship
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.student_application.domain.repository.ApplicationRepository


class GetUpdateApplicationUseCase (
    private val repository: ApplicationRepository
){
    operator fun invoke(applicationID: String, status: String
    ): Flow<Result<Boolean>> = flow {

        try {
            emit(Result.Loading<Boolean>())
            val result = repository.studentUpdateApplication(applicationID, status)
            emit(Result.Success<Boolean>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.message())
            emit(Result.Error<Boolean>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<Boolean>(e.toString()))
        }
    }
}