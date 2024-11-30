package com.polytech.polytechnfc.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.User
import com.polytech.polytechnfc.model.UserBadge
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {
    private val _usersState = MutableStateFlow<List<UserBadge>>(emptyList())
    val usersState: StateFlow<List<UserBadge>>  = _usersState

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            val users = firestoreService.getUsers()
            _usersState.value = users
        }
    }

    fun updateUser(user: UserBadge){
        viewModelScope.launch {
            try{
                firestoreService.updateUser(user)
                val updateUsers = usersState.value.map{
                    if (it.id == user.id) user else it
                }
                _usersState.value = updateUsers
            }
            catch(e: Exception){
                Log.e("UsersViewModel", "Error updating user: ${e.message}")
            }
        }
    }

}