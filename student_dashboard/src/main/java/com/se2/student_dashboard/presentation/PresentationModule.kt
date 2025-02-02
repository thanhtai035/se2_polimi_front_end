package com.se2.student_dashboard.presentation

import com.se2.student_dashboard.presentation.viewmodel.AddInternshipViewModel
import com.se2.student_dashboard.presentation.viewmodel.ApplicationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.se2.student_dashboard.presentation.viewmodel.DashboardViewModel
import com.se2.student_dashboard.presentation.viewmodel.UniversityViewModel

internal val presentationModule = module {
    viewModelOf(::DashboardViewModel)
    viewModelOf(::AddInternshipViewModel)
    viewModelOf(::ApplicationViewModel)
    viewModelOf(::UniversityViewModel)

}
