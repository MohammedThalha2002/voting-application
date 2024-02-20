package com.example.voting.services

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface UserService {
    fun getAllUsers(): List<User>
    fun findUserById(userName : String) : User
    fun postUser(@RequestBody user: User) : User
    fun deleteUser(@PathVariable userId : Long): HttpResponse
    fun vote(@PathVariable userId : Long, @PathVariable partyId : Long) : HttpResponse
}