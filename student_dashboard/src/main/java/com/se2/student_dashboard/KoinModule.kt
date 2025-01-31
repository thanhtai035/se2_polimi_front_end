package com.se2.student_dashboard

import com.se2.student_dashboard.data.dataModule
import com.se2.student_dashboard.domain.domainModule
import com.se2.student_dashboard.presentation.presentationModule

val featureDashboardModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
