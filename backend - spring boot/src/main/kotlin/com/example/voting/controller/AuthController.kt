package com.example.voting.controller

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val userRepository : UserRepository) {

    @PostMapping("/sign-in")
    fun signIn(@RequestBody user: User) : HttpResponse {
        // checking for user with same name already exists
        val foundUsers : List<User> = userRepository.findByName(user.name)

        if(foundUsers.isEmpty()){
            userRepository.save(user)
            println("AUTH : user saved to the db")
            return HttpResponse(message = "Signed In successfully")
        }

        throw CustomException("Username already exists")
    }

    @PostMapping("/login")
    fun login(@RequestBody user: User) : HttpResponse {
        // checking for users existence
        val foundUsers : List<User> = userRepository.findByName(user.name)

        if(foundUsers.isEmpty()){
            throw CustomException("User not found")
        }
        // checking the password
        if(foundUsers[0].password != user.password){
            throw CustomException("Incorrect Password")
        }

        return HttpResponse(message = "Logged in successfully")
    }
}