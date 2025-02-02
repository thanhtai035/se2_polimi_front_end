package com.se2.student_dashboard.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.se2.base.Common.Result
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto
import com.se2.student_dashboard.domain.use_case.GetPostInterviewUseCase
import com.se2.student_dashboard.domain.use_case.GetPostQuestionnaireUseCase
import com.se2.student_dashboard.domain.use_case.GetUpdateStatusUseCase
import com.se2.student_dashboard.domain.use_case.PostInternshipUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ApplicationViewModel (
    private val getPostInterviewUseCase: GetPostInterviewUseCase,
    private val getPostQuestionnaireUseCase: GetPostQuestionnaireUseCase,
    private val getUpdateStatusUseCase: GetUpdateStatusUseCase
) : ViewModel() {
    val postInterviewResponse : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val postQuestionnaireResponse : MutableLiveData<Result<Boolean>> = MutableLiveData()
    val updateStatusResponse : MutableLiveData<Result<Boolean>> = MutableLiveData()


    fun postInterview(interviewFormDto: InterviewFormDto) {
        getPostInterviewUseCase(interviewFormDto).onEach {result ->
            postInterviewResponse.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun postQuestionnaire(questionnaireFormDto: QuestionnaireFormDto) {
        getPostQuestionnaireUseCase(questionnaireFormDto).onEach {result ->
            postQuestionnaireResponse.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun updateStatus(applicationID:String, statusUpdateDto: StatusUpdateDto) {
        getUpdateStatusUseCase(applicationID, statusUpdateDto).onEach {result ->
            updateStatusResponse.postValue(result)
        }.launchIn(viewModelScope)
    }
}



