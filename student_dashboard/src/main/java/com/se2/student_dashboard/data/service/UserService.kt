package com.se2.student_dashboard.data.service

import com.se2.base.model.dto.UserDto
import com.se2.student_dashboard.data.dto.UniBodyHolderDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UserService {

    @POST("/api/v1/users/university")
    suspend fun getStudents(@Body uniBodyHolderDto: UniBodyHolderDto) : List<UserDto>
}

