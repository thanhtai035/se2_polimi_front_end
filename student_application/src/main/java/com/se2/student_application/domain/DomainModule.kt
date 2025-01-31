package com.se2.student_application.domain

import com.se2.student_application.domain.use_case.GetUpdateApplicationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetUpdateApplicationUseCase)
}
