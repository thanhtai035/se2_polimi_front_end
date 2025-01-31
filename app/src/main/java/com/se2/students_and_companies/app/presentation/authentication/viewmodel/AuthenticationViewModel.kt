package com.se2.students_and_companies.app.presentation.authentication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.se2.base.model.User
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.base.Common.Result
import com.se2.students_and_companies.app.domain.use_case.GetAuthenticationResponseUseCase
import com.se2.students_and_companies.app.domain.use_case.GetRegisterUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthenticationViewModel (
    private val getAuthenticationResponseUseCase: GetAuthenticationResponseUseCase,
    private val getRegisterUseCase: GetRegisterUseCase
) : ViewModel()
{
    val response: MutableLiveData<Result<User>> = MutableLiveData()
    val registerResponse: MutableLiveData<Result<Boolean>> = MutableLiveData()

    // Get response from Log In post request
    public fun logIn(username: String, password: String) {
        getAuthenticationResponseUseCase(username, password).onEach { result ->
            response.postValue(result)
        }.launchIn(viewModelScope)
    }

    public fun register(user: RegisterStudentDto) {
        getRegisterUseCase(user).onEach { result ->
            registerResponse.postValue(result)
        }.launchIn(viewModelScope)
    }
}