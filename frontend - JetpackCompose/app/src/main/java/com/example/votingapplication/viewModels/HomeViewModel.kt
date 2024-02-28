package com.example.votingapplication.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.votingapplication.entity.Party
import com.example.votingapplication.entity.User
import com.example.votingapplication.services.ErrorHandlingService
import com.example.votingapplication.services.RetrofitService
import kotlinx.coroutines.launch

class UserViewModelFactory(private val username: String, private val navController: NavController) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeViewModel(username, navController) as T
}

class HomeViewModel(private val username: String,private val navController: NavController) : ViewModel() {

    var selectedParty by mutableStateOf(Party())
    var user by mutableStateOf(User())
    var parties : MutableList<Party> = mutableListOf()
    var votingStatus by mutableStateOf("")

    private fun getUserByUsername() {
        Log.d("USER", username)
        viewModelScope.launch {
            try {
                val response = RetrofitService.authAPI.getUserByName(username)
                if(response.isSuccessful && response.body() != null){
                    user = response.body()!!
                    if(user.isVoted){
                        navController.navigate("voted")
                    }
                    Log.e("USER RESPONSE IS", user.name)
                } else {
                    navController.navigate("login")
                }
            } catch (e : Exception){
                Log.d("RESPONSE-ERROR", e.message.toString())
            }
        }
    }

    init {
        runInitialRequirements()
    }

    private fun runInitialRequirements(){
        viewModelScope.launch {getUserByUsername() }
        viewModelScope.launch {getParties() }
        viewModelScope.launch {getVotingStatus() }
    }

    private fun getParties(){
        viewModelScope.launch {
            try {
                val response = RetrofitService.partyAPI.getParties()
                if(response.isSuccessful && response.body() != null){
                    parties.clear()
                    parties.addAll(response.body()!!)
                } else {
                    Log.d("RESPONSE-ERROR", response.errorBody().toString())
                }
            } catch (e : Exception){
                Log.d("RESPONSE-ERROR", e.message.toString())
            }
        }
    }

    private fun getVotingStatus(){
        viewModelScope.launch {
            try {
                val response = RetrofitService.partyAPI.getVotingStatus()
                if(response.isSuccessful && response.body() != null){
                    votingStatus = response.body()!!.message
                } else {
                    Log.d("RESPONSE-ERROR", response.errorBody().toString())
                }
            } catch (e : Exception){
                Log.d("RESPONSE-ERROR", e.message.toString())
            }
        }
    }

    fun onVoteButtonClicked(context: Context, navController: NavController){
        if(votingStatus == "NOT_STARTED"){
            Toast.makeText(context, "Wait! Voting is not started",
                Toast.LENGTH_SHORT)
                .show()
            return
        }

        if(votingStatus == "ENDED"){
            Toast.makeText(context, "Voting is ended. U can't vote",
                Toast.LENGTH_SHORT)
                .show()
            navController.navigate("results")
            return
        }

        if(selectedParty.partyName == ""){
            Toast.makeText(context, "No party selected",
                Toast.LENGTH_SHORT)
                .show()
            return
        }

        vote(context)
    }

    fun vote(context: Context){
        viewModelScope.launch {
            try {
                val response = RetrofitService.partyAPI.vote(user.userId, selectedParty.partyId)
                if(response.isSuccessful && response.body() != null){
                    navController.navigate("results")
                } else {
                    val error = ErrorHandlingService(response).getError()
                    Toast.makeText(context, error.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e : Exception){
                Toast.makeText(context, e.message,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}