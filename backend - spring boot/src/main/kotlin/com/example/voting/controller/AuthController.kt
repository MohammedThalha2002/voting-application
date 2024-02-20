package com.example.voting.controller

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import com.example.voting.services.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authServices: AuthService) {

    @PostMapping("/sign-in")
    fun signIn(@RequestBody user: User) : HttpResponse {
        return authServices.signIn(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User) : HttpResponse {
        return authServices.login(user)
    }
}