package com.se2.account_profile.presentation.ui.ui.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackResponseDto
import com.se2.account_profile.presentation.ui.ui.domain.use_case.GetSendFeedbackUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.se2.base.Common.Result


class ProfileViewModel (
    private val getSendFeedbackUseCase: GetSendFeedbackUseCase
) : ViewModel() {

    val sendFeedbackResponse: MutableLiveData<Result<Boolean>> = MutableLiveData()

    fun sendFeedback(feedbackDto: FeedbackDto) {
        getSendFeedbackUseCase(feedbackDto).onEach { result ->
            sendFeedbackResponse.postValue(result)
        }.launchIn(viewModelScope)
    }

}



