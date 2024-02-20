package com.example.voting.services

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

interface PartyService {
    fun getAllUsers(): List<Party>
    fun addParty(@RequestBody party: Party): HttpResponse
    fun deleteParty(@PathVariable partyId : Long): HttpResponse
    fun getVotingStatus(): HttpResponse
    fun changeVotingStatus(@PathVariable status : String) : HttpResponse
}