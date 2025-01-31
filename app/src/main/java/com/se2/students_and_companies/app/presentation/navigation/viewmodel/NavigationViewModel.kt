package com.se2.students_and_companies.app.presentation.navigation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.base.model.User
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.base.Common.Result
import com.se2.base.model.Application
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import com.se2.students_and_companies.app.domain.use_case.GetAuthenticationResponseUseCase
import com.se2.students_and_companies.app.domain.use_case.GetCompanyApplicationUseCase
import com.se2.students_and_companies.app.domain.use_case.GetRegisterUseCase
import com.se2.students_and_companies.app.domain.use_case.GetStudentApplicationUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class NavigationViewModel (
    private val getCompanyApplicationUseCase: GetCompanyApplicationUseCase,
    private val getStudentApplicationUseCase: GetStudentApplicationUseCase
) : ViewModel()
{
    val listApplicationResponse: MutableLiveData<Result<List<Application>>> = MutableLiveData()

    fun getStudentApplication(studentApplicationHolderDto: StudentApplicationHolderDto) {
        getStudentApplicationUseCase(studentApplicationHolderDto).onEach {
            result ->
            listApplicationResponse.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun getCompanyApplication(userID: String) {
        getCompanyApplicationUseCase(userID).onEach {
                result ->
            listApplicationResponse.postValue(result)
        }.launchIn(viewModelScope)
    }
}