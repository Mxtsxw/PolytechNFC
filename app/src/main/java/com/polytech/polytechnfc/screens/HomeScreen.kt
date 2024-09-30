package com.polytech.polytechnfc.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.polytech.polytechnfc.ViewModel.SignOutViewModel
import com.polytech.polytechnfc.screens.Sign_in.SignInScreen
import com.polytech.polytechnfc.screens.destinations.SignInScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator,
               viewModel : SignOutViewModel = koinViewModel()
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text("Bienvenue sur Polytech NFC")
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