package com.example.voting.controller

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.entity.User
import com.example.voting.entity.VotingResult
import com.example.voting.repository.PartyRepository
import com.example.voting.repository.UserRepository
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
class PartyController(val partyRepository : PartyRepository, val userRepository : UserRepository) {

    @GetMapping("/parties")
    fun getAllUsers(): List<Party> {
        try {
            return partyRepository.findAll()
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    @PostMapping("/party")
    fun addParty(@RequestBody party: Party): HttpResponse {
        // checking for party name existence
        val foundParty : List<Party> = partyRepository.findByPartyName(party.partyName)

        if(foundParty.isEmpty()){
            partyRepository.save(party)
            return HttpResponse(message = "Party added successfully")
        }

        throw CustomException(message = "Party name already exists")
    }

    @DeleteMapping("/party/{partyId}")
    fun deleteUser(@PathVariable partyId : Long): HttpResponse {
        val party : Optional<Party> = partyRepository.findById(partyId);
        if(!party.isEmpty){
            partyRepository.delete(party.get())
            return HttpResponse(message = "Party deleted successfully")
        }

        throw CustomException(message = "Party not found")
    }

    @GetMapping("/voting/status")
    fun getVotingStatus(): HttpResponse{
        return HttpResponse(message = VotingResult.status)
    }

    @PostMapping("/voting/status/{status}")
    fun changeVotingStatus(@PathVariable status : String) : HttpResponse{
        VotingResult.status = status
        val users : List<User> = userRepository.findAll()
        val parties : List<Party> = partyRepository.findAll()
        if(status == "STARTED"){
            // change all users isVoting field to false
            for(user in users){
                user.isVoted = false
            }
            // change all party totalVotes to zero
            for (party in parties){
                party.totalVotes = 0
            }
        } else if(status == "ENDED") {
            // change all users isVoting field to true
            for(user in users){
                user.isVoted = true
            }
        }
        userRepository.saveAll(users)
        partyRepository.saveAll(parties)
        return HttpResponse(message = VotingResult.status)
    }
}