package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.polytech.polytechnfc.ViewModel.CardsViewModel
import com.polytech.polytechnfc.screens.components.CustomListItemComponent
import com.polytech.polytechnfc.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel


@Destination
@Composable
fun CardsScreen(
    navigator: DestinationsNavigator,
    viewModel: CardsViewModel = koinViewModel()
) {
    val badgeInfos by viewModel.badgeInfosState.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween, // Espacement entre les sections
    ) {
        // Titre
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
        ){
            Text(
                text = "Liste des badges",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        // Liste des badges
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(badgeInfos) { badgeInfo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Nom: ${badgeInfo.name}, UID: ${badgeInfo.uid}",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
