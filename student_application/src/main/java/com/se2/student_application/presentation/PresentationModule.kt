package com.se2.student_application.presentation

import com.se2.student_application.presentation.viewmodel.ApplicationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::ApplicationViewModel)

}
