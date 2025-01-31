package com.se2.account_profile.presentation.ui.ui

import com.se2.account_profile.presentation.ui.ui.data.dataModule
import com.se2.account_profile.presentation.ui.ui.domain.domainModule
import com.se2.account_profile.presentation.ui.ui.presentation.presentationModule

val featureProfileModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
