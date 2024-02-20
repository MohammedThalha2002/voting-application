package com.example.voting.controller

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.entity.User
import com.example.voting.repository.PartyRepository
import com.example.voting.repository.UserRepository
import com.example.voting.services.UserServices
import org.springframework.web.bind.annotation.*
import java.util.Optional

@CrossOrigin
@RestController
class UserController(val userServices: UserServices) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        return userServices.getAllUsers()
    }

    @GetMapping("/user/{userName}")
    fun findUserById(@PathVariable userName : String) : User {
        return userServices.findUserById(userName)
    }

    @PostMapping("/user")
    fun postUser(@RequestBody user: User) : User{
        return userServices.postUser(user)
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId : Long): HttpResponse {
        return userServices.deleteUser(userId)
    }

    @GetMapping("/user/vote/{userId}/{partyId}")
    fun vote(@PathVariable userId : Long, @PathVariable partyId : Long) : HttpResponse {
        return userServices.vote(userId, partyId)
    }
}