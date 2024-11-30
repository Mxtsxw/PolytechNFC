package com.polytech.polytechnfc.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.UsersViewModel
import com.polytech.polytechnfc.model.UserBadge
import com.polytech.polytechnfc.screens.components.CustomListItemComponent
import com.polytech.polytechnfc.screens.components.UserEditDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun UsersScreen(
    navigator: DestinationsNavigator,
    viewModel: UsersViewModel = koinViewModel()
) {
    val users by viewModel.usersState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val selectedUser = remember { mutableStateOf<UserBadge?>(null) }


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
                    onThreeDotsClick = {
                        selectedUser.value = user
                        showDialog.value = true
                    },
                )
            }
        }
    }

    if(showDialog.value) {
        UserEditDialog(
            user = selectedUser.value!!,
            onDismiss = { showDialog.value = false },
            onUpdate = {
                updatedUser ->
                Log.d("UsersScreen", "User to update: $updatedUser")
                viewModel.updateUser(updatedUser)
                showDialog.value = false
            }
        )
    }




}