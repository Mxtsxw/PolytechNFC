package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.RoomsListViewModel
import com.polytech.polytechnfc.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun RoomsListScreen(navigator: DestinationsNavigator,
                    viewModel : RoomsListViewModel = koinViewModel()) {

    val rooms by viewModel.roomsState.collectAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Liste des salles",
            style = MaterialTheme.typography.headlineMedium
        )


        Spacer(modifier = Modifier.height(16.dp))

        //Liste des salles
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rooms) { room ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Salle : ${room.name}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton de retour
        Button(
            onClick = { navigator.navigate(HomeScreenDestination) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour")
        }
    }
}



