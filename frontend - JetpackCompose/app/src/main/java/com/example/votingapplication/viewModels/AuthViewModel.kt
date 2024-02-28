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
import com.example.votingapplication.entity.User
import com.example.votingapplication.services.ErrorHandlingService
import com.example.votingapplication.services.RetrofitService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class AuthViewModel  : ViewModel() {

    var screen by mutableStateOf("HOME")
    var users : MutableList<User> = mutableStateListOf()

    fun changeScreen(value : String){
        screen = value
    }

    fun onBackClicked(){
        screen = "HOME"
    }

    init {
        getUsers()
    }

    private fun getUsers(){
        viewModelScope.launch {
            try {
                val response = RetrofitService.authAPI.getUsers()

                if(response.isSuccessful){
                    users.clear();
                    users.addAll(response.body()!!)
                    Log.d("RESPONSE", response.body().toString())
                } else {
                    Log.d("RESPONSE-ERROR", response.errorBody().toString())
                }
            } catch (e : Exception){
                Log.d("RESPONSE-ERROR", e.message.toString())
            }
        }
    }

    // SIGN IN
    fun signIn(user: User, context: Context, navController: NavController){
        // 1. check if already a user with tha same username is exists
        // 2. if not then add the user to the db
        // 3. else throw an assertion error
        Log.d("SignIn-AuthViewModel", "Adding new user")

        val handler = CoroutineExceptionHandler{_, throwable ->
            // SHOW TOAST
            Toast.makeText(context, throwable.message,
                Toast.LENGTH_SHORT)
                .show()
        }

        viewModelScope.launch(handler) {
            try {
                val response  = RetrofitService.authAPI.signIn(user)

                if(response.isSuccessful){
                    Log.d("SignIn-AuthViewModel", "New user added")
                    navController.navigate("home/${user.name}")
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

    fun login(user: User, context : Context, navController: NavController){
        // 1. find the user with the same username
        // 2. if not throw error - user not found
        // 3. else check for the password
        // 4. if doesn't match throw error

        val handler = CoroutineExceptionHandler{_, throwable ->
            // SHOW TOAST
            Toast.makeText(context, throwable.message,
                Toast.LENGTH_SHORT)
                .show()
        }

        viewModelScope.launch(handler) {
           try {
               val response = RetrofitService.authAPI.login(user)

               if(response.isSuccessful){
                   Log.d("SignIn-AuthViewModel", "New user added ${user.name}")
                   navController.navigate("home/${user.name}")
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

    // DELETE A USER
    fun deleteUser(user : User, context : Context){

        viewModelScope.launch {
            try {
                val response = RetrofitService.authAPI.deleteUser(user.userId)

                if(response.isSuccessful){
                    getUsers();
                    Log.d("AUTH RESPONSE", response.body()!!.message)
                } else {
                    // show toast
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