package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.ReadersViewModel
import com.polytech.polytechnfc.screens.components.CustomListItemComponent
import com.polytech.polytechnfc.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun ReadersScreen(
    navigator: DestinationsNavigator,
    viewModel: ReadersViewModel = koinViewModel()
) {
    val readerIds by viewModel.readerIdsState.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        // Title
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
        ){
            Text(
                text = "Liste des lecteurs NFC",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // List of NFC Readers
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(readerIds) { readerId ->
                CustomListItemComponent(
                    title = "Lecteur NFC $readerId",
                )
            }
        }
    }
}
