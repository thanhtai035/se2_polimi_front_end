package com.example.recommendation.domain.use_case

import Internship
import android.util.Log
import com.example.recommendation.domain.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result


class GetSecondRecommendationUseCase (
    private val repository: RecommendationRepository
){
    operator fun invoke(userID: Int
    ): Flow<Result<List<Internship>>> = flow {

        try {
            emit(Result.Loading<List<Internship>>())
            val result = repository.getSecondRecommendation(userID)
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