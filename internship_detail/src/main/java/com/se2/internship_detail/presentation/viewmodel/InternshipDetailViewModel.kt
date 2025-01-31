package com.se2.internship_detail.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.internship_detail.domain.model.User
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.se2.base.Common.Result
import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.domain.use_case.GetSendApplicationUseCase

class InternshipDetailViewModel (
    private val getSendApplicationUseCase: GetSendApplicationUseCase,
) : ViewModel() {

    val sendApplicationResponse: MutableLiveData<Result<Boolean>> = MutableLiveData()

    fun sendApplication(applicationDto: ApplicationDto) {
        getSendApplicationUseCase(applicationDto).onEach { result ->
            sendApplicationResponse.postValue(result)
        }.launchIn(viewModelScope)
    }

}



