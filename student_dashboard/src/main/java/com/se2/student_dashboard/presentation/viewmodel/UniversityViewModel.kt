package com.se2.student_dashboard.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.se2.base.Common.Result
import com.se2.base.model.User
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto
import com.se2.student_dashboard.domain.use_case.GetPostInterviewUseCase
import com.se2.student_dashboard.domain.use_case.GetPostQuestionnaireUseCase
import com.se2.student_dashboard.domain.use_case.GetStudentListUseCase
import com.se2.student_dashboard.domain.use_case.GetUpdateStatusUseCase
import com.se2.student_dashboard.domain.use_case.PostInternshipUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UniversityViewModel (
    private val getStudentListUseCase: GetStudentListUseCase
) : ViewModel() {
    val response : MutableLiveData<Result<List<User>>> = MutableLiveData()

    fun getStudents(uniName: String) {
        getStudentListUseCase(uniName).onEach {result ->
            response.postValue(result)
        }.launchIn(viewModelScope)
    }
}



