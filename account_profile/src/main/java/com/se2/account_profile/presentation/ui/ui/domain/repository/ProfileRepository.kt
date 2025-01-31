package com.se2.account_profile.presentation.ui.ui.domain.repository

import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackResponseDto


interface ProfileRepository {
    suspend fun sendFeedback(feedbackDto: FeedbackDto): Boolean

}