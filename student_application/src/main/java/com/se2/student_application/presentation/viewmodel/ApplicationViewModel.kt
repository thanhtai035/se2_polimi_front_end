package com.se2.student_application.presentation.viewmodel

import Internship
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.se2.base.Common.Result
import com.se2.student_application.domain.use_case.GetUpdateApplicationUseCase


class ApplicationViewModel (
    private val getUpdateApplicationUseCase: GetUpdateApplicationUseCase
) : ViewModel() {

    val response: MutableLiveData<Result<Boolean>> = MutableLiveData()

    fun updateApplication(applicationID: String, status: String) {
        getUpdateApplicationUseCase(applicationID, status).onEach { result ->
            response.postValue(result)
        }.launchIn(viewModelScope)
    }
}



