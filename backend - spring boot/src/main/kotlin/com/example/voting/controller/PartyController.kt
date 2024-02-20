package com.example.voting.controller

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.services.PartyServices
import org.springframework.web.bind.annotation.*

@RestController
class PartyController(val partyServices: PartyServices) {

    @GetMapping("/parties")
    fun getAllUsers(): List<Party> {
        return partyServices.getAllUsers()
    }

    @PostMapping("/party")
    fun addParty(@RequestBody party: Party): HttpResponse {
        return partyServices.addParty(party)
    }

    @DeleteMapping("/party/{partyId}")
    fun deleteParty(@PathVariable partyId : Long): HttpResponse {
        return partyServices.deleteParty(partyId)
    }

    @GetMapping("/voting/status")
    fun getVotingStatus(): HttpResponse {
        return partyServices.getVotingStatus()
    }

    @PostMapping("/voting/status/{status}")
    fun changeVotingStatus(@PathVariable status : String) : HttpResponse {
        return partyServices.changeVotingStatus(status)
    }
}