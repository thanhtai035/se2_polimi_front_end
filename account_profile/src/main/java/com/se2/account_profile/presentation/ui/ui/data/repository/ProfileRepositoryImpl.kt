package com.se2.account_profile.presentation.ui.ui.data.repository

import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackResponseDto
import com.se2.account_profile.presentation.ui.ui.data.service.ProfileService
import com.se2.account_profile.presentation.ui.ui.domain.repository.ProfileRepository


class ProfileRepositoryImpl (
    private val apiService: ProfileService
) : ProfileRepository {
    override suspend fun sendFeedback(feedbackDto: FeedbackDto): Boolean {
        return try {
            apiService.sendFeedback(feedbackDto)
            true
        } catch (e: Exception)
        {return false}
    }

}