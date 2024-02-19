package com.example.votingapplication.repository

import android.app.Application
import com.example.votingapplication.entity.Party
import com.example.votingapplication.entity.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmRepository : Application() {
//    companion object {
//        lateinit var realm: Realm
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        realm = Realm.open(
//            configuration = RealmConfiguration.create(
//                schema = setOf(
//                    User::class,
//                    Party::class
//                )
//            )
//        )
//    }
}