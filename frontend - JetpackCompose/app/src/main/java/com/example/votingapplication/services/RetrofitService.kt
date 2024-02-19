package com.example.votingapplication.services

import com.example.votingapplication.services.api.authAPI
import com.example.votingapplication.services.api.partyAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    var baseUrl = "http://172.24.110.199:8080"

    val authAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(authAPI::class.java)

    val partyAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(partyAPI::class.java)
}