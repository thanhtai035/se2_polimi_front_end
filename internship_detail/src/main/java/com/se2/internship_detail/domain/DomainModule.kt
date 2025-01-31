package com.se2.internship_detail.domain

import com.se2.internship_detail.domain.use_case.GetSendApplicationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetSendApplicationUseCase)


}
