package com.se2.student_dashboard.domain.use_case

import Internship
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result
import com.se2.base.model.User
import com.se2.student_dashboard.domain.repository.InternshipRepository

import com.se2.student_dashboard.domain.repository.UserRepository

class GetAllInternshipUseCase (
    private val repository: InternshipRepository
){
    operator fun invoke(
    ): Flow<Result<List<Internship>>> = flow {

        try {
            emit(Result.Loading<List<Internship>>())
            val result = repository.getAllInternship()
            emit(Result.Success<List<Internship>>(result))
        } catch(e: HttpException) {
            Log.d("tai", e.message())
            emit(Result.Error<List<Internship>>(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            Log.d("tai", e.toString())
            emit(Result.Error<List<Internship>>(e.toString()))
        }
    }
}