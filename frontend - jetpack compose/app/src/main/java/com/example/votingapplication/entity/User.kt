package com.example.votingapplication.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.UUID

class User {
    @PrimaryKey
    var name : String = ""
    var userId : Long = 0
    var password : String = ""
    var isVoted : Boolean = false
}