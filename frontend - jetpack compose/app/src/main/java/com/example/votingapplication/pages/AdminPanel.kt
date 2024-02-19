package com.example.votingapplication.pages

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.votingapplication.entity.Party
import com.example.votingapplication.utils.generateRandomColor
import com.example.votingapplication.viewModels.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminPanel(navController: NavController){

    val adminViewModel : AdminViewModel = viewModel<AdminViewModel>()

    var partyName by remember {
        mutableStateOf("")
    }

    val parties = adminViewModel.parties.sortedByDescending { it.totalVotes }
    val context = LocalContext.current

    fun onSubmit(){
        val party : Party = Party().apply {
            this.partyName = partyName
        }
        adminViewModel.addNewParty(party, context)
        partyName = ""
    }

    Scaffold {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = generateRandomColor()[5],
                titleContentColor = Color.White,
            ),
            title = { Text("Admin Panel") },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                    text = "'Democracy is a Government of the People, by the People, and for the People'",
                    modifier = Modifier
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            VoteControlBtn(adminViewModel, context, navController)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "ADD A PARTY", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = partyName,
                onValueChange = { partyName = it },
                label = { Text(text = "Enter the Party Name") })
            Spacer(modifier = Modifier.height(6.dp))
            Button(onClick = {
                onSubmit()
            }) {
                Text(text = "Add Party")
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
                content = {
                    items(parties) { party ->
                        PartyCards(party, adminViewModel, context)
                    }
                },
                modifier = Modifier.fillMaxSize()
                )
        }
    }
}

@Composable
fun VoteControlBtn(adminViewModel: AdminViewModel, context: Context, navController: NavController){
    when(adminViewModel.votingStatus){
        "NOT_STARTED", "ENDED" -> Button(onClick = {
            adminViewModel.changeVotingState("STARTED", context, navController)
        }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text(text = "Start Voting")
        }
        "STARTED" -> Button(onClick = {
            adminViewModel.changeVotingState("ENDED" ,context, navController)
        }, colors = ButtonDefaults.buttonColors(Color.Red)) {
            Text(text = "END Voting")
        }
    }
}

@Composable
fun PartyCards(party: Party, adminViewModel: AdminViewModel, context: Context){

    val randomIndex by remember {
        mutableStateOf((0..<generateRandomColor().size).random())
    }

    Log.d("ADMIN", party.totalVotes.toString())

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = party.partyName,
                    modifier = Modifier
                        .padding(start = 16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {
                    adminViewModel.deleteParty(party, context)
                }) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Delete",
                    )
                }
            }
            Text(
                text = "${party.totalVotes}",
                modifier = Modifier
                    .padding(start = 90.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp
            )
        }
    }
}
