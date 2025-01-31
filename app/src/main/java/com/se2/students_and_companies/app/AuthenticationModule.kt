package com.se2.students_and_companies.app

import com.se2.students_and_companies.app.data.dataModule
import com.se2.students_and_companies.app.domain.domainModule
import com.se2.students_and_companies.app.presentation.presentationModule


val authenticationModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
