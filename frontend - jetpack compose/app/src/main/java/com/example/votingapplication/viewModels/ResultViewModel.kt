package com.example.votingapplication.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapplication.entity.Party
import com.example.votingapplication.repository.RealmRepository
import com.example.votingapplication.services.RetrofitService
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class ResultViewModel : ViewModel() {

    var votingStatus by mutableStateOf("")
    var winnerParty by mutableStateOf(Party())

    init {
        getVotingStatus()
    }

    private fun getVotingStatus(){
        viewModelScope.launch {
            val response = RetrofitService.partyAPI.getVotingStatus()
            if(response.isSuccessful && response.body() != null){
                votingStatus = response.body()!!.message
                getWinningParties()
            } else {
                Log.d("RESPONSE-ERROR", response.errorBody().toString())
            }
        }
    }

    private fun getWinningParties(){
        viewModelScope.launch {
            val response = RetrofitService.partyAPI.getParties()
            if(response.isSuccessful && response.body() != null){
                var parties : List<Party> = response.body()!!
                parties = parties.sortedByDescending { it.totalVotes }
                winnerParty.apply {
                    partyName = parties[0].partyName
                    totalVotes = parties[0].totalVotes
                }
            } else {
                Log.d("RESPONSE-ERROR", response.errorBody().toString())
            }
        }
    }
}