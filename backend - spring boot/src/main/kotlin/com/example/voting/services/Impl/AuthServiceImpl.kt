package com.example.voting.services.Impl

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import com.example.voting.services.AuthServices
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(private val userRepository : UserRepository) : AuthServices {
    override fun signIn(user: User): HttpResponse {
        // checking for user with same name already exists
        val foundUsers : List<User> = userRepository.findByName(user.name)

        if(foundUsers.isEmpty()){
            userRepository.save(user)
            println("AUTH : user saved to the db")
            return HttpResponse(message = "Signed In successfully")
        }

        throw CustomException("Username already exists")
    }

    override fun login(user: User): HttpResponse {
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