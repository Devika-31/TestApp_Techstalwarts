package com.india.testingapp.api


import com.india.testingapp.models.request.RequestData
import com.india.testingapp.models.response.LoginResponse
import com.india.testingapp.models.response.UserListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginAPI {
    @POST("/api/login")
    suspend fun login(@Body request: RequestData): Response<LoginResponse>

    @GET("/api/users?page=1")
    suspend fun getUserList(): Response<UserListResponse>
}