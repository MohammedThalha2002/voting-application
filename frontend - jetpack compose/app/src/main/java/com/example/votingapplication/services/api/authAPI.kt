package com.example.votingapplication.services.api

import com.example.votingapplication.entity.CustomResponse
import com.example.votingapplication.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface authAPI {
    @GET("/users")
    suspend fun getUsers() : Response<List<User>>

    @GET("/user/{name}")
    suspend fun getUserByName(@Path("name") name : String) : Response<User>

    @POST("/auth/sign-in")
    suspend fun signIn(@Body user : User) : Response<CustomResponse>

    @POST("/auth/login")
    suspend fun login(@Body user : User) : Response<CustomResponse>

    @DELETE("/user/{id}")
    suspend fun deleteUser(@Path("id") userId : Long) : Response<CustomResponse>
}