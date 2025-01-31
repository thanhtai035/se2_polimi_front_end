package com.example.recommendation.presentation.viewmodel

import Internship
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recommendation.domain.use_case.GetFirstRecommendationUseCase
import com.example.recommendation.domain.use_case.GetSecondRecommendationUseCase
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import com.se2.base.Common.Result


class RecommendationViewModel (
    private val getFirstRecommendationUseCase: GetFirstRecommendationUseCase,
    private val getSecondRecommendationUseCase: GetSecondRecommendationUseCase
) : ViewModel() {

    val firstRecommendationInternshipList: MutableLiveData<Result<List<Internship>>> = MutableLiveData()
    val secondRecommendationInternshipList: MutableLiveData<Result<List<Internship>>> = MutableLiveData()

    fun getFirstRecommendation(userID: Int) {
        getFirstRecommendationUseCase(userID).onEach { result ->
            firstRecommendationInternshipList.postValue(result)
        }.launchIn(viewModelScope)
    }

    fun getSecondRecommendation(userID: Int) {
        getSecondRecommendationUseCase(userID).onEach { result ->
            secondRecommendationInternshipList.postValue(result)
        }.launchIn(viewModelScope)
    }
}



