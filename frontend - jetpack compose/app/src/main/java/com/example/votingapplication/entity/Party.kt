package com.example.votingapplication.entity

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Party {
    var partyId : Long = 0
    var partyName : String = ""
    var totalVotes : Int = 0
    var voters : RealmList<User> = realmListOf()
}