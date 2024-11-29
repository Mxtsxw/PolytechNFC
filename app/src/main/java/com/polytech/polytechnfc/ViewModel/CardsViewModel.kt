package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.BadgeInfo
import com.polytech.polytechnfc.model.service.FirestoreService
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardsViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {

    private val _badgeInfosState = MutableStateFlow<List<BadgeInfo>>(emptyList())
    val badgeInfosState: StateFlow<List<BadgeInfo>> = _badgeInfosState

    init {
        fetchBadgeInfos()
    }

    private fun fetchBadgeInfos() {
        viewModelScope.launch {
            val badgeInfos = firestoreService.getBadgeInfos()
            _badgeInfosState.value = badgeInfos
        }
    }
}