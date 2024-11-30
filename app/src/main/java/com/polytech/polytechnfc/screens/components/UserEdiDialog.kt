package com.polytech.polytechnfc.screens.components



import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.AccessViewModel
import com.polytech.polytechnfc.ViewModel.CardsViewModel
import com.polytech.polytechnfc.model.BadgeInfo
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.UserBadge
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserEditDialog(
    user: UserBadge,
    onDismiss: () -> Unit,
    onUpdate: (UserBadge) -> Unit,
    viewModel: AccessViewModel = koinViewModel(),
    viewModelCards: CardsViewModel = koinViewModel()
) {
    var firstname by remember { mutableStateOf(user.firstname) }
    var lastname by remember { mutableStateOf(user.lastname) }
    var selectedBadge by remember { mutableStateOf(user.badge?.uid ?: "") }
    var selectedRole by remember { mutableStateOf(user.role?.label ?: "") }

    var selectedRoleId by remember { mutableStateOf(user.role?.id ?: "") }
    var selectedBadgeId by remember { mutableStateOf(user.badge?.id ?: "") }

    val badgeUids by viewModelCards.badgeInfosState.collectAsState(initial = emptyList())
    val roles by remember { mutableStateOf(viewModel.roles) }


    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val updatedUser = user.copy(
                    firstname = firstname,
                    lastname = lastname,
                    badge = BadgeInfo(
                        id = selectedBadgeId,
                        name = "",
                        uid = selectedBadge
                    ),
                    role = Role(
                        id = selectedRoleId,
                        label = selectedRole
                    )


                )
                Log.d("UserEditDialog", "updatedUser: $updatedUser")
                onUpdate(updatedUser)
            }) {
                Text("Valider")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annuler")
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "$firstname $lastname",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                // Champ pour le prénom
                TextField(
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = { Text("Prénom") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Champ pour le nom
                TextField(
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = { Text("Nom") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Liste déroulante pour les badges (UIDs)
                DropdownField(
                    label = "Badge",
                    selectedItem = selectedBadge,
                    onItemSelect = { selectedUid ->
                        selectedBadge = selectedUid
                        //ID correspondant au label selectionné
                        val selectedBadgeObj = badgeUids.find { it.uid == selectedBadge }
                        selectedBadgeId = selectedBadgeObj?.id ?: ""
                    },
                    items = badgeUids.map { it.uid }
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Liste déroulante pour les rôles
                DropdownField(
                    label = "Rôle",
                    selectedItem = selectedRole,
                    onItemSelect = { selectedLabel -> selectedRole = selectedLabel

                    //ID correspondant au label selectionné
                    val selectedRoleObj = roles.find { it.label == selectedRole }
                    selectedRoleId = selectedRoleObj?.id ?: ""
            },
                items = roles.map { it.label }
                )
            }
        }
    )
}


