package com.example.voting.services.Impl

import com.example.voting.common.CustomException
import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.entity.User
import com.example.voting.entity.VotingResult
import com.example.voting.repository.PartyRepository
import com.example.voting.repository.UserRepository
import com.example.voting.services.PartyServices
import org.springframework.stereotype.Service
import java.util.*

@Service
class PartyServiceImpl(val partyRepository: PartyRepository, val userRepository: UserRepository) : PartyServices {
    override fun getAllUsers(): List<Party> {
        try {
            return partyRepository.findAll()
        } catch (ex: Exception) {
            throw CustomException(ex.message.toString())
        }
    }

    override fun addParty(party: Party): HttpResponse {
        // checking for party name existence
        val foundParty : List<Party> = partyRepository.findByPartyName(party.partyName)

        if(foundParty.isEmpty()){
            partyRepository.save(party)
            return HttpResponse(message = "Party added successfully")
        }

        throw CustomException(message = "Party name already exists")
    }

    override fun deleteParty(partyId: Long): HttpResponse {
        val party : Optional<Party> = partyRepository.findById(partyId);
        if(!party.isEmpty){
            partyRepository.delete(party.get())
            return HttpResponse(message = "Party deleted successfully")
        }

        throw CustomException(message = "Party not found")
    }

    override fun getVotingStatus(): HttpResponse {
        return HttpResponse(message = VotingResult.status)
    }

    override fun changeVotingStatus(status: String): HttpResponse {
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