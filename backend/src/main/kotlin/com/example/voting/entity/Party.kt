package com.example.voting.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Party(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val partyId : Long,
    val partyName : String,
    var totalVotes : Int = 0
)