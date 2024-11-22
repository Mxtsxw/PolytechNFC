package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.google.firebase.firestore.QuerySnapshot
import com.polytech.polytechnfc.ViewModel.RecordsViewModel
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.screens.Sign_in.SignInScreen
import com.polytech.polytechnfc.screens.destinations.SignInScreenDestination
import com.polytech.polytechnfc.service.FirestoreService
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator,
               viewModel : SignOutViewModel = koinViewModel(),
               viewModelFirestore: RecordsViewModel = koinViewModel()
) {
    val records by viewModelFirestore.recordsState.collectAsState(emptyList())

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text="Bienvenue sur Polytech NFC",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Historique des badges scannés",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))


        //Affichage des données réucpérées de la base de données Firestore
        LazyColumn (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(records) { record ->
                val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(record.timestamp)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = MaterialTheme.shapes.medium
                ){
                    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                        Text(
                            text = "ID : ${record.id}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Timestamp : $formattedDate",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))



        //Boutton pour se déconnecter
        Button(
            onClick= {
                viewModel.signOut()
                navigator.navigate(SignInScreenDestination())
            },
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text("Se déconnecter")
        }

    }
}