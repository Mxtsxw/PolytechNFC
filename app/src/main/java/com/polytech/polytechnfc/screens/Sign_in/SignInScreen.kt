package com.polytech.polytechnfc.screens.Sign_in

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavController
import com.polytech.polytechnfc.R
import com.polytech.polytechnfc.ViewModel.SignInState
import com.polytech.polytechnfc.ViewModel.SignInViewModel
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import com.polytech.polytechnfc.screens.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Destination(start = true)
@Composable
fun SignInScreen(navigator: DestinationsNavigator,
                 viewModel: SignInViewModel = koinViewModel(),
                 snackbarHostState: SnackbarHostState,
                 snackbarScope: CoroutineScope,
                 showTopBar: MutableState<Boolean>
) {
    showTopBar.value = false

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val signInState by viewModel.signinState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Centers the content in both horizontal and vertical directions
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Se connecter",
                style = MaterialTheme.typography.headlineLarge, // Larger text style
                color = MaterialTheme.colorScheme.primary // Optional: Adjust color
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Champs email
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Champs mot de passe
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Mot de passe") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Cacher le mot de passe" else "Afficher le mot de passe"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gestion de l'état de connexion
            when (signInState) {
                is SignInState.Initial -> {}
                is SignInState.Loading -> {
                    CircularProgressIndicator()
                }

                is SignInState.Success -> {
                    navigator.navigate(HomeScreenDestination())
                }

                is SignInState.Error -> {
                    Text(
                        text = (signInState as SignInState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Bouton de connexion
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        // Affichez le Snackbar si les champs sont vides
                        snackbarScope.launch {
                            snackbarHostState.showSnackbar("Veuillez remplir tous les champs.")
                        }
                    } else {
                        viewModel.signIn(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Se Connecter")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

