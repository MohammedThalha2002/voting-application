package com.example.votingapplication.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.votingapplication.services.RetrofitService
import kotlinx.coroutines.launch
import com.example.votingapplication.entity.Party as Party1

class ResultViewModel : ViewModel() {

    var votingStatus by mutableStateOf("")
    var winnerParty by mutableStateOf(Party1())

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
                var parties : List<Party1> = response.body()!!
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