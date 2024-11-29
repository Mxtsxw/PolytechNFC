package com.polytech.polytechnfc.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.RecordsViewModel
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.screens.components.RecordCard
import com.polytech.polytechnfc.screens.destinations.AccessCreateScreenDestination
import com.polytech.polytechnfc.screens.destinations.CardsScreenDestination
import com.polytech.polytechnfc.screens.destinations.ReadersScreenDestination
import com.polytech.polytechnfc.screens.destinations.RolesScreenDestination
import com.polytech.polytechnfc.screens.destinations.RoomsListScreenDestination
import com.polytech.polytechnfc.screens.destinations.UsersScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator,
               viewModel : SignOutViewModel = koinViewModel(),
               viewModelFirestore: RecordsViewModel = koinViewModel(),
               showTopBar: MutableState<Boolean>
) {
    showTopBar.value = true

    val records by viewModelFirestore.recordsState.collectAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF3F3F3)),
        verticalArrangement = Arrangement.Center
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Text(
                text = "Historique des accès",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        //Affichage des données réucpérées de la base de données Firestore
        LazyColumn(modifier = Modifier
            .height(screenHeight / 2) // Set height to half of the screen
            .fillMaxWidth(),          // Make it span the full width
        ) {
            items(records.take(10)) { record ->
                val formattedDate = SimpleDateFormat(
                    "dd/MM/yyyy HH:mm:ss",
                    Locale.getDefault()
                ).format(record.timestamp)

                RecordCard(
                    user = "Inconnu",
                    timestamp = formattedDate,
                    room = "record.room",
                    granted = record.granted
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFECECEC))
        )
        {
            Text(
                text = "Naviguer",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
                    .background(Color(0xECECEC)),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // First row with "Salles" and "Cartes"
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Card Salles
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Set background color to white
                            ),
                            onClick = {
                                navigator.navigate(RoomsListScreenDestination())
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Salles",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // Card Cartes
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Set background color to white
                            ),
                            onClick = {
                                navigator.navigate(CardsScreenDestination())
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Cartes",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                // Second row with "Rôles" and "Lecteurs"
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Card Rôles
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Set background color to white
                            ),
                            onClick = {
                                navigator.navigate(RolesScreenDestination())
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Rôles",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        // Card Lecteurs
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Set background color to white
                            ),
                            onClick = {
                                navigator.navigate(ReadersScreenDestination())
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Lecteurs",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ){
                        //Card Utilisateurs
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White // Set background color to white
                            ),
                            onClick = {
                                navigator.navigate(UsersScreenDestination())
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Utilisateurs",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }


                // Single card for "Créer un accès"
                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White // Set background color to white
                        ),
                        onClick = {
                            navigator.navigate(AccessCreateScreenDestination())
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Créer un accès",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

        }

//
//
//            //Boutton pour se déconnecter
//            Button(
//                onClick = {
//                    viewModel.signOut()
//                    navigator.navigate(SignInScreenDestination())
//                },
//                modifier = Modifier.fillMaxWidth()
//            )
//            {
//                Text("Se déconnecter")
//            }

        }
    }

