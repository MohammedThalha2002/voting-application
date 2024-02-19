package com.example.voting.repository

import com.example.voting.entity.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository : JpaRepository<Party, Long> {
    fun findByPartyName(partyName : String) : List<Party>
}