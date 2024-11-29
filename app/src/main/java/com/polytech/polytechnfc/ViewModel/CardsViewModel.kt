package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.service.FirestoreService
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {

    private val _badgeIdsState = MutableStateFlow<List<String>>(emptyList())
    val badgeIdsState: StateFlow<List<String>> = _badgeIdsState

    init {
        fetchBadgeIds()
    }

    private fun fetchBadgeIds() {
        viewModelScope.launch {
            val badgeIds = firestoreService.getBadgeIds()
            _badgeIdsState.value = badgeIds
        }
    }
}