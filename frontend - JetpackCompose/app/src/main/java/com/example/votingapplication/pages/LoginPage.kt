package com.example.votingapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.votingapplication.components.LoginInput
import com.example.votingapplication.viewModels.AuthViewModel

@Composable
fun LoginPage(
    navController: NavController
){

    val authViewModel : AuthViewModel = viewModel<AuthViewModel>()

    when(authViewModel.screen){
        "HOME" -> HomeScreen(authViewModel)
        "ADMIN" -> AdminLogin(authViewModel, navController)
        "USER" -> UserLogin(authViewModel, navController)
    }

}

@Composable
fun HomeScreen(authViewModel: AuthViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            authViewModel.changeScreen("ADMIN")
        }) {
            Text("ADMIN")
        }
        Button(onClick = {
            authViewModel.changeScreen("USER")
        }) {
            Text("USER")
        }
    }
}

@Composable
fun AdminLogin(authViewModel: AuthViewModel, navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Admin Login")
        LoginInput(role = "Admin", authViewModel, navController)
    }
}

@Composable
fun UserLogin(authViewModel: AuthViewModel,navController: NavController,){
    val users = authViewModel.users
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn{
            items(users) {user ->
                ClickableText(text = AnnotatedString(user.name + " " + user.userId), onClick = {
                    authViewModel.deleteUser(user, context)
                })
            }
        }
        Text(text = "User Login")
        LoginInput(role = "User", authViewModel, navController)
    }
}