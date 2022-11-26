package com.hacked.app.retrofit

import com.hacked.app.retrofit.data.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/user/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/user/register")
    fun registration(@Body request: RegistrationRequest): Call<RegistrationResponse>

    @POST("/user/verify-email")
    fun confirm(@Body request: ConfirmRequest): Call<ConfirmResponse>

    @GET("/user/profile")
    fun getUser(): Call<UserResponse>

    @PUT("/user/profile/update")
    fun updateUserName(@Body request: UserNameRequest): Call<UserResponse>
}