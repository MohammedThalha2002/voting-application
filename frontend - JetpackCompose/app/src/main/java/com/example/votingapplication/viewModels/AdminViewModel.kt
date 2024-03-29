package com.example.votingapplication.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.votingapplication.entity.Party
import com.example.votingapplication.services.ErrorHandlingService
import com.example.votingapplication.services.RetrofitService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    var parties : MutableList<Party> = mutableStateListOf()
    var votingStatus by mutableStateOf("")
    var showDialog by mutableStateOf(false)
    var selectedParty : Party? by mutableStateOf(null)

    init {
        getParties()
        getVotingStatus()
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
            }  catch (e : Exception){
                Log.d("RESPONSE-ERROR", e.message.toString())
            }
        }
    }

    // ADD NEW PARTY
    fun addNewParty(party : Party, context: Context){

        if(votingStatus == "STARTED"){
            Toast.makeText(context, "Party cannot be added as voting is started",
                Toast.LENGTH_SHORT)
                .show()
            return;
        }

        val handler = CoroutineExceptionHandler{_, throwable ->
            // SHOW TOAST
            Toast.makeText(context, throwable.message,
                Toast.LENGTH_SHORT)
                .show()
        }

        viewModelScope.launch(handler) {
            try {
                val response  = RetrofitService.partyAPI.addParty(party)

                if(response.isSuccessful){
                    getParties()
                    Log.d("ADMINViewModel", "New party added")
                } else {
                    val error = ErrorHandlingService(response).getError()
                    Toast.makeText(context, error.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }  catch (e : Exception){
                Toast.makeText(context, e.message,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun deleteParty(context: Context){
        if(votingStatus == "STARTED"){
            Toast.makeText(context, "Party cannot be deleted as voting is started",
                Toast.LENGTH_SHORT)
                .show()
            return;
        }

        showDialog = true;
        viewModelScope.launch {
            val response  = RetrofitService.partyAPI.deleteParty(selectedParty!!.partyId)

            if(response.isSuccessful){
                getParties()
                selectedParty = Party()
                Log.d("ADMINViewModel", "Party deleted")
            } else {
                val error = ErrorHandlingService(response).getError()
                Toast.makeText(context, error.message,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun changeVotingState(state : String,context: Context, navController: NavController){
        viewModelScope.launch {
            try {
                val response  = RetrofitService.partyAPI.endVoting(state)
                if(response.isSuccessful){
                    votingStatus = state
                    if(state == "ENDED") {
                        navController.navigate("results")
                    }
                    if(state == "STARTED"){
                        getParties()
                    }
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