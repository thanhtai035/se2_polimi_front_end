package com.se2.student_dashboard.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.base.model.User

import com.se2.base.Common.Result
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.domain.use_case.PostInternshipUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AddInternshipViewModel (
    private val postInternshipUseCase: PostInternshipUseCase
) : ViewModel() {
    val postInternshipResponse: MutableLiveData<Result<Boolean>> = MutableLiveData()

    fun postInternship(internshipFormDto: InternshipFormDto) {
        postInternshipUseCase(internshipFormDto).onEach {result ->
            postInternshipResponse.postValue(result)
        }.launchIn(viewModelScope)
    }
}



