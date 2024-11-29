package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.UsersViewModel
import com.polytech.polytechnfc.screens.components.CustomListItemComponent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun UsersScreen(
    navigator: DestinationsNavigator,
    viewModel: UsersViewModel = koinViewModel()
) {
    val users by viewModel.usersState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Liste des utilisateurs",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(users) { user ->
                CustomListItemComponent(
                    title = user.email,
                    dynamicContent = listOf(
                        "Nom: ${user.lastname}",
                        "Prénom: ${user.firstname}",
                        "Rôle: ${user.role?.label}",
                        "Badge: ${user.badge?.uid}"
                    ),
                    icon = Icons.Default.Person,
                    onThreeDotsClick = {},
                )
            }
        }
    }




}