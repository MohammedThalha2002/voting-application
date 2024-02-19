package com.example.voting.repository

import com.example.voting.entity.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name : String) : List<User>
}