package com.se2.account_profile.presentation.ui.ui.domain.use_case

import android.util.Log
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackResponseDto
import com.se2.account_profile.presentation.ui.ui.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import com.se2.base.Common.Result


class GetSendFeedbackUseCase (
    private val repository: ProfileRepository
){
    operator fun invoke(feedbackDto: FeedbackDto
    ): Flow<Result<Boolean>> = flow {

        try {
            emit(Result.Loading<Boolean>())
            val result = repository.sendFeedback(feedbackDto)
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