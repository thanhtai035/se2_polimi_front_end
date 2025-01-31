package com.se2.internship_detail.presentation

import com.se2.internship_detail.presentation.viewmodel.InternshipDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::InternshipDetailViewModel)

}
