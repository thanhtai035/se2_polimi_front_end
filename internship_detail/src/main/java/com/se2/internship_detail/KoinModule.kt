package com.se2.internship_detail

import com.se2.internship_detail.data.dataModule
import com.se2.internship_detail.domain.domainModule
import com.se2.internship_detail.presentation.presentationModule

val featureInternshipDetailModule = listOf(
    presentationModule,
    domainModule,
    dataModule,
)
