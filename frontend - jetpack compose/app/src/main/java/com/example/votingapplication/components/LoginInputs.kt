package com.example.votingapplication.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.votingapplication.entity.User
import com.example.votingapplication.viewModels.AuthViewModel
import java.lang.AssertionError

@Composable
fun LoginInput(role : String, authViewModel: AuthViewModel, navController: NavController){
    var username by remember {
        mutableStateOf("admin")
    }
    var password by remember {
        mutableStateOf("12345")
    }

    val context = LocalContext.current

    fun onSubmit(type : String){
        if(username == "" || password == "" ){
            Toast.makeText(context, "Username and Password should not be empty",
                Toast.LENGTH_SHORT)
                .show()
            return
        }

        if(role == "Admin"){
            // check username == admin and password == admin@123
            // then route to the admin panel
            if (username == "admin" && password == "12345"){
                navController.navigate("admin-panel")
            } else {
                // show toast or snack-bar
                Toast.makeText(context, "Invalid Admin Credentials",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            // check type - login or signUp
            val user : User = User().apply {
                this.name = username
                this.password = password
            }
            if(type == "LOGIN"){
                authViewModel.login(user, context, navController)
            } else{
                authViewModel.signIn(user, context, navController)
            }
        }

    }

    OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text(text = "Username")})
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password")})
    Spacer(modifier = Modifier.height(20.dp))
    if(role == "Admin"){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = {
                authViewModel.onBackClicked()
            }) {
                Text(text = "BACK")
            }
            Button(onClick = {
                onSubmit("ADMIN")
            }) {
                Text(text = "LOGIN")
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = {
                authViewModel.onBackClicked()
            }) {
                Text(text = "BACK")
            }
            Button(onClick = {
                onSubmit("SIGN UP")
            }) {
                Text(text = "SIGN UP")
            }
            Button(onClick = {
                onSubmit("LOGIN")
            }) {
                Text(text = "LOGIN")
            }
        }
    }
}