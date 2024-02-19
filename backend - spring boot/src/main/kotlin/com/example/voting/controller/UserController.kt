package com.example.voting.controller

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.entity.User
import com.example.voting.repository.PartyRepository
import com.example.voting.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.Optional

@CrossOrigin
@RestController
class UserController(val userRepository : UserRepository, val partyRepository: PartyRepository) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> {
        try {
            return userRepository.findAll()
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    @GetMapping("/user/{userName}")
    fun findUserById(@PathVariable userName : String) : User {
        val user : List<User> = userRepository.findByName(userName)
        if(user.isNotEmpty()){
            return user[0]
        }
        throw CustomException("User not found")
    }

    @PostMapping("/user")
    fun postUser(@RequestBody user: User) : User{
        try {
            return userRepository.save(user)
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    @DeleteMapping("/user/{userId}")
    fun deleteUser(@PathVariable userId : Long): HttpResponse {
        val user : Optional<User> = userRepository.findById(userId);
        if(!user.isEmpty){
            userRepository.delete(user.get())
            return HttpResponse(message = "User deleted successfully")
        }
        throw CustomException("User not found")
    }

    @GetMapping("/user/vote/{userId}/{partyId}")
    fun vote(@PathVariable userId : Long, @PathVariable partyId : Long) : HttpResponse {
        val user : Optional<User> = userRepository.findById(userId);
        val party : Optional<Party> = partyRepository.findById(partyId);

        if(user.isPresent && party.isPresent){
            user.get().isVoted = true;
            userRepository.save(user.get())
            party.get().totalVotes += 1
            partyRepository.save(party.get())
            return HttpResponse(message = "Voted successfully")
        }
        throw CustomException("Something went wrong")
    }
}