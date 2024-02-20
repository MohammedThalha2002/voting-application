package com.example.voting.controller

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.services.PartyService
import org.springframework.web.bind.annotation.*

@RestController
class PartyController(val partyService: PartyService) {

    @GetMapping("/parties")
    fun getAllUsers(): List<Party> {
        return partyService.getAllUsers()
    }

    @PostMapping("/party")
    fun addParty(@RequestBody party: Party): HttpResponse {
        return partyService.addParty(party)
    }

    @DeleteMapping("/party/{partyId}")
    fun deleteParty(@PathVariable partyId : Long): HttpResponse {
        return partyService.deleteParty(partyId)
    }

    @GetMapping("/voting/status")
    fun getVotingStatus(): HttpResponse {
        return partyService.getVotingStatus()
    }

    @PostMapping("/voting/status/{status}")
    fun changeVotingStatus(@PathVariable status : String) : HttpResponse {
        return partyService.changeVotingStatus(status)
    }
}