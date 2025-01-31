package com.se2.student_dashboard.domain.repository

import Internship
import InternshipResponse
import com.se2.base.model.User
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto


interface InternshipRepository {
    suspend fun getAllInternship() : List<Internship>

    suspend fun postInternship(internshipFormDto: InternshipFormDto): Boolean

}