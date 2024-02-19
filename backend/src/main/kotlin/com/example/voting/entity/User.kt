package com.example.voting.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId : Long,
    val name : String,
    val password : String,
    var isVoted : Boolean = false
)