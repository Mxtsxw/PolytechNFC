package com.polytech.polytechnfc.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel (
    private val accountService : AccountServiceImpl
): ViewModel() {

    private val _signinState = MutableStateFlow<SignInState>(SignInState.Initial)
    val signinState : StateFlow<SignInState> = _signinState.asStateFlow()

    //Fonction pour gérer la connexion
    fun signIn(email: String, password: String) {
        _signinState.value = SignInState.Loading
        viewModelScope.launch {
            try {
                accountService.signIn(email, password)
                _signinState.value = SignInState.Success
            } catch (e: Exception) {
                _signinState.value = SignInState.Error("Erreur lors de la connexion")
            }
        }
    }

}