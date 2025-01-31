package com.se2.internship_detail.domain.use_case

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.domain.repository.ApplicationRepository

class GetSendApplicationUseCase (
    private val repository: ApplicationRepository
){
    operator fun invoke(applicationDto: ApplicationDto
    ): Flow<Result<Boolean>> = flow {

        try {
            emit(Result.Loading<Boolean>())
            val result = repository.sendApplication(applicationDto)
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