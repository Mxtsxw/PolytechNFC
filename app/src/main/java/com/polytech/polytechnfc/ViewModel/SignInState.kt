package com.polytech.polytechnfc.ViewModel

open class SignInState {
    object Initial : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}