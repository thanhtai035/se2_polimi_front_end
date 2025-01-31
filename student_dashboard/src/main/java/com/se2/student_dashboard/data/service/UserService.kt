package com.se2.student_dashboard.data.service

import com.se2.base.model.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path


interface UserService {

    @GET("/api/users/{userID}")
    suspend fun getUser(@Path("userID") userID: String) : UserDto
}

