package com.polytech.polytechnfc.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.AccessViewModel
import com.polytech.polytechnfc.screens.components.DropdownField
import com.polytech.polytechnfc.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun AccessCreateScreen(
    navigator: DestinationsNavigator,
    viewModel: AccessViewModel = koinViewModel(),
    snackbarHostState: SnackbarHostState,
    snackbarScope: CoroutineScope
) {
    // Titre de la page (création d'un accès)
    var showDialog by remember { mutableStateOf(false) }

    val rooms = viewModel.rooms
    val roles = viewModel.roles
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Création des accès",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            DropdownField(
                label = "Rôle*",
                selectedItem = viewModel.role,
                onItemSelect = { viewModel.role = it },
                items = roles.map { it.label }
            )

            DropdownField(
                label = "Salle*",
                selectedItem = viewModel.room,
                onItemSelect = { viewModel.room = it },
                items = rooms.map { it.name }
            )



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerField(
                    label = "Date de début",
                    selectedDate = viewModel.startDate,
                    onDateChange = { viewModel.startDate = it }
                )

                TimePickerField(
                    label = "Heure de début",
                    selectedTime = viewModel.startTime,
                    onTimeChange = { viewModel.startTime = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerField(
                    label = "Date de fin",
                    selectedDate = viewModel.endDate,
                    onDateChange = { viewModel.endDate = it }
                )

                TimePickerField(
                    label = "Heure de fin",
                    selectedTime = viewModel.endTime,
                    onTimeChange = { viewModel.endTime = it }
                )
            }
        }



        Button(
            onClick = {
                if (viewModel.role.isNotEmpty() && viewModel.room.isNotEmpty()) {
                    showDialog = true
                } else {
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar("Veuillez remplir tous les champs obligatoires *")
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ) {
            Text("Créer l'accès")
        }
        Button(
            onClick = { navigator.navigate(HomeScreenDestination) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text("Retour à l'accueil")
        }





        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        viewModel.createAccess(
                            onSuccess = {
                                Log.d("AccessCreateScreen", "Access creation success")
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar("Accès créé avec succès")
                                    Log.d("Access2", "Access created")
                                }
                            },
                            onError = {
                                snackbarScope.launch {
                                    snackbarHostState.showSnackbar("Erreur lors de la création de l'accès")
                                }

                            }

                        )
                    }) {
                        Text("Confirmer")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Annuler")
                    }
                },
                title = { Text("Vérification des données") },
                text = {
                    Column {
                        Text("Rôle : ${viewModel.role}")
                        Text("Salle : ${viewModel.room}")
                        Text("Début : ${viewModel.startDate} à ${viewModel.startTime}")
                        Text("Fin : ${viewModel.endDate} à ${viewModel.endTime}")
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerField(label: String, selectedDate: LocalDate, onDateChange: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateChange(LocalDate.of(year, month + 1, dayOfMonth))
            },
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        )
    }

    OutlinedButton(onClick = { datePickerDialog.show() },
        modifier = Modifier.height(56.dp)
        ) {
        Text("$label: ${selectedDate.toString()}")
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerField(label: String, selectedTime: LocalTime, onTimeChange: (LocalTime) -> Unit) {
    val context = LocalContext.current
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeChange(LocalTime.of(hour, minute))
            },
            selectedTime.hour,
            selectedTime.minute,
            true
        )
    }

    OutlinedButton(onClick = { timePickerDialog.show() },
        modifier = Modifier.height(56.dp)
        ) {
        Text("$label: ${selectedTime.toString()}")
    }
}

