package com.example.voting.services.Impl

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.entity.User
import com.example.voting.repository.PartyRepository
import com.example.voting.repository.UserRepository
import com.example.voting.services.UserServices
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val userRepository : UserRepository, val partyRepository: PartyRepository) : UserServices {
    override fun getAllUsers(): List<User> {
        try {
            return userRepository.findAll()
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    override fun findUserById(userName: String): User {
        val user : List<User> = userRepository.findByName(userName)
        if(user.isNotEmpty()){
            return user[0]
        }
        throw CustomException("User not found")
    }

    override fun postUser(user: User): User {
        try {
            return userRepository.save(user)
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    override fun deleteUser(userId: Long): HttpResponse {
        val user : Optional<User> = userRepository.findById(userId);
        if(!user.isEmpty){
            userRepository.delete(user.get())
            return HttpResponse(message = "User deleted successfully")
        }
        throw CustomException("User not found")
    }

    override fun vote(userId: Long, partyId: Long): HttpResponse {
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