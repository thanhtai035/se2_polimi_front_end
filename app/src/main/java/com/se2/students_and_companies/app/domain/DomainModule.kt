package com.se2.students_and_companies.app.domain


import com.se2.students_and_companies.app.domain.use_case.GetAuthenticationResponseUseCase
import com.se2.students_and_companies.app.domain.use_case.GetCompanyApplicationUseCase
import com.se2.students_and_companies.app.domain.use_case.GetRegisterUseCase
import com.se2.students_and_companies.app.domain.use_case.GetStudentApplicationUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::GetAuthenticationResponseUseCase)
    singleOf(::GetRegisterUseCase)
    singleOf(::GetCompanyApplicationUseCase)
    singleOf(::GetStudentApplicationUseCase)
}
