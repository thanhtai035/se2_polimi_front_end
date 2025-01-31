package com.se2.students_and_companies.app.presentation

import com.se2.students_and_companies.app.presentation.authentication.viewmodel.AuthenticationViewModel
import com.se2.students_and_companies.app.presentation.navigation.viewmodel.NavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::AuthenticationViewModel)
    viewModelOf(::NavigationViewModel)
}
