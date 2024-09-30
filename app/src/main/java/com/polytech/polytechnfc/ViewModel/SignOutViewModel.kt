package com.polytech.polytechnfc.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.service.module.AccountServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignOutViewModel (
    private val accountService : AccountServiceImpl
): ViewModel() {

    private val _signoutState = MutableStateFlow<SignOutState>(SignOutState.Initial)
    val signoutState : StateFlow<SignOutState> = _signoutState.asStateFlow()

    //Fonction pour gérer la déconnexion
    fun signOut() {
        _signoutState.value = SignOutState.Loading
        viewModelScope.launch {
            accountService.signOut()
            _signoutState.value = SignOutState.Success
        }
    }

}