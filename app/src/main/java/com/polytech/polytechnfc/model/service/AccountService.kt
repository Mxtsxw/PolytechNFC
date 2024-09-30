package com.polytech.polytechnfc.model.service

import com.polytech.polytechnfc.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()
}