package com.example.votingapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.votingapplication.pages.AdminPanel
import com.example.votingapplication.pages.AllReadyVotedPage
import com.example.votingapplication.pages.HomePage
import com.example.votingapplication.pages.LoginPage
import com.example.votingapplication.pages.ResultPage
import com.example.votingapplication.ui.theme.VotingApplicationTheme
import com.example.votingapplication.utils.generateRandomColor

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstancescreen: Bundle?) {
        super.onCreate(savedInstancescreen)
        setContent {
            VotingApplicationTheme {
                val navController = rememberNavController()
                Scaffold {
                    val scrollState = rememberScrollState()
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = generateRandomColor()[5],
                            titleContentColor = Color.White,
                        ),
                        title = { Text("Voting Application") }
                    )

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginPage(navController) }
                        composable("admin-panel") { AdminPanel(navController) }
                        composable("home/{userId}") {backStackEntry ->
                            val userId : String = backStackEntry.arguments?.getString("userId") ?: ""
                            HomePage(navController, userId, scrollState)
                        }
                        composable("voted") { AllReadyVotedPage(navController) }
                        composable("results") { ResultPage(navController) }
                    }
                }
            }
        }
    }
}