package com.se2.account_profile.presentation.ui.ui.domain

import com.se2.account_profile.presentation.ui.ui.domain.use_case.GetSendFeedbackUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetSendFeedbackUseCase)
}
