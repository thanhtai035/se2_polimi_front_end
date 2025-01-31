package com.se2.student_dashboard.domain


import com.se2.student_dashboard.domain.use_case.GetAllInternshipUseCase
import com.se2.student_dashboard.domain.use_case.PostInternshipUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::PostInternshipUseCase)
    singleOf(::GetAllInternshipUseCase)
}
