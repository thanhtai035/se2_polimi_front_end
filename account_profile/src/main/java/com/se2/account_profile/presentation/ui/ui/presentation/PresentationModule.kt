package com.se2.account_profile.presentation.ui.ui.presentation

import com.se2.account_profile.presentation.ui.ui.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val presentationModule = module {
    viewModelOf(::ProfileViewModel)

}
