package com.se2.account_profile.presentation.ui.ui.data.service

import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {

    @POST("/api/v1/feedbacks")
    suspend fun sendFeedback(@Body feedbackDto: FeedbackDto)
}

