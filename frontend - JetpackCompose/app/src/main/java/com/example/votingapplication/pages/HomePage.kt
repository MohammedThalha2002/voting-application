package com.example.votingapplication.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.votingapplication.entity.Party
import com.example.votingapplication.utils.generateRandomColor
import com.example.votingapplication.viewModels.HomeViewModel
import com.example.votingapplication.viewModels.UserViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(navController: NavController, username : String, scrollState: ScrollState){

    val context = LocalContext.current
    val homeViewModel : HomeViewModel = viewModel<HomeViewModel>(factory = UserViewModelFactory(username, navController))
    val user = homeViewModel.user

    Scaffold {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = generateRandomColor()[5],
                titleContentColor = Color.White,
            ),
            modifier = Modifier.zIndex(1f),
            title = { Text("Home Page") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Arrow",
                        tint = Color.White
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .verticalScroll(state = scrollState)
                .padding(all = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
            ) {
                Text(
                    text = "'The one sure way of participating in the process of nation-building is to vote on the election day.'",
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Select the party to vote", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Text(text = "ID : ${user.userId}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Text(text = "NAME : ${user.name}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Text(text = "Voted : ${user.isVoted}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Column{
                homeViewModel.parties.forEach{party ->
                    VotingPartyCards(party, homeViewModel)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                // voting
                homeViewModel.onVoteButtonClicked(context, navController)
            }) {
                Text(text = "VOTE")
            }
        }
    }
}

@Composable
fun VotingPartyCards(party: Party, homeViewModel: HomeViewModel){

    val randomIndex by remember {
        mutableIntStateOf((0..<generateRandomColor().size).random())
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = generateRandomColor()[randomIndex],
        ),
        modifier = Modifier
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .selectable(
                        selected = party == homeViewModel.selectedParty,
                        onClick = {
                            homeViewModel.selectedParty = party
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = party.partyName,
                    modifier = Modifier
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                RadioButton(
                    selected = party == homeViewModel.selectedParty,
                    onClick = {
                        homeViewModel.selectedParty = party
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.White
                    )
                )
            }
        }
    }
}
