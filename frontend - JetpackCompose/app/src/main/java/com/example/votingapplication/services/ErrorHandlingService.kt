package com.example.votingapplication.services

import android.widget.Toast
import com.example.votingapplication.entity.CustomResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

class ErrorHandlingService(response : Response<CustomResponse>, private var error : CustomResponse = CustomResponse()) {
    init {
        val gson = Gson()
        val type = object : TypeToken<CustomResponse>() {}.type
        error = gson.fromJson(response.errorBody()!!.charStream(), type)
    }

    fun getError(): CustomResponse {
        return error
    }
}