package com.example.votingapplication.pages

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.votingapplication.components.AlertDialogBox
import com.example.votingapplication.entity.Party
import com.example.votingapplication.utils.generateRandomColor
import com.example.votingapplication.viewModels.AdminViewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AdminPanel(navController: NavController, scrollState: ScrollState){

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
            modifier = Modifier.zIndex(1f),
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
        if(adminViewModel.showDialog){
            AlertDialogBox(
                onDismissRequest = { adminViewModel.showDialog = false },
                onConfirmation = {
                    adminViewModel.deleteParty(context)
                    adminViewModel.showDialog = false
                    println("Deleted Successfully") // Add logic here to handle confirmation.
                },
                dialogTitle = "Delete the Party",
                dialogText = "Do you want to delete the party ${adminViewModel.selectedParty!!.partyName}",
                icon = Icons.Default.Delete
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 25.dp
            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {
                for (party in parties) {
                    PartyCards(party, adminViewModel, itemSize)
                }
            }
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
fun PartyCards(party: Party, adminViewModel: AdminViewModel, itemSize: Dp){

    val randomIndex by remember {
        mutableStateOf((0..<generateRandomColor().size).random())
    }

    Log.d("ADMIN", party.totalVotes.toString())

    Card(
        colors = CardDefaults.cardColors(
            containerColor = generateRandomColor()[randomIndex],
        ),
        modifier = Modifier
            .padding(5.dp).size(itemSize),
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
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                IconButton(onClick = {
                    adminViewModel.selectedParty = party
                    adminViewModel.showDialog = true
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
                fontSize = 70.sp
            )
        }
    }
}
