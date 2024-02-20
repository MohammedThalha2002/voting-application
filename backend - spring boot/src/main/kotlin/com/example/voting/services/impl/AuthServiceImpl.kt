package com.example.voting.services.impl

import com.example.voting.common.Bcrypt
import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.User
import com.example.voting.repository.UserRepository
import com.example.voting.services.AuthService
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(private val userRepository : UserRepository) : AuthService {
    override fun signIn(user: User): HttpResponse {
        // checking for user with same name already exists
        val foundUsers : List<User> = userRepository.findByName(user.name)

        if(foundUsers.isEmpty()){
            user.apply {
                password = Bcrypt.hash(user.password + user.name)
            }
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

        if(!Bcrypt.compare(user.password, foundUsers[0].password)){
            throw CustomException("Incorrect Password")
        }

        return HttpResponse(message = "Logged in successfully")
    }
}