package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RolesViewModel(
    private val firestoreService: FirestoreServiceImpl
): ViewModel() {

    private val _rolesState = MutableStateFlow<List<Role>>(emptyList())
    val rolesState: StateFlow<List<Role>> = _rolesState

    init {
        fetchRoles()
    }

    private fun fetchRoles(){
        viewModelScope.launch {
            val roles = firestoreService.getRoles()
            _rolesState.value = roles
        }
    }


}