package com.example.voting.services.Impl

import com.example.voting.dao.HttpResponse
import com.example.voting.entity.Party
import com.example.voting.services.PartyServices

class PartyServiceImpl : PartyServices {
    override fun getAllUsers(): List<Party> {
        TODO("Not yet implemented")
    }

    override fun addParty(party: Party): HttpResponse {
        TODO("Not yet implemented")
    }

    override fun deleteParty(partyId: Long): HttpResponse {
        TODO("Not yet implemented")
    }

    override fun getVotingStatus(): HttpResponse {
        TODO("Not yet implemented")
    }

    override fun changeVotingStatus(status: String): HttpResponse {
        TODO("Not yet implemented")
    }
}