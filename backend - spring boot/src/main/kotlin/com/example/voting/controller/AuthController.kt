package com.example.voting.controller

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import com.example.voting.services.AuthServices
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authServices: AuthServices) {

    @PostMapping("/sign-in")
    fun signIn(@RequestBody user: User) : HttpResponse {
        return authServices.signIn(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User) : HttpResponse {
        return authServices.login(user)
    }
}