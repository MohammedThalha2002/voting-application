package com.example.voting.services

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User

interface AuthServices {
    fun signIn(user: User) : HttpResponse
    fun login(user: User) : HttpResponse
}