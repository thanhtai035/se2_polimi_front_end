package com.se2.student_dashboard.presentation.viewmodel

import Internship
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.base.model.User

import com.se2.base.Common.Result
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.domain.use_case.GetAllInternshipUseCase
import com.se2.student_dashboard.domain.use_case.PostInternshipUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DashboardViewModel (
    private val getAllInternshipUseCase: GetAllInternshipUseCase
) : ViewModel() {

    val internshipList: MutableLiveData<Result<List<Internship>>> = MutableLiveData()

    fun initialize() {
        getAllInternship()
    }

    private fun getAllInternship() {
        getAllInternshipUseCase().onEach { result ->
            internshipList.postValue(result)
        }.launchIn(viewModelScope)
    }
}



