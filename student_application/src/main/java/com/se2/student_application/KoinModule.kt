package com.se2.student_application

import com.se2.student_application.data.dataModule
import com.se2.student_application.domain.domainModule
import com.se2.student_application.presentation.presentationModule

val featureApplicationModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
