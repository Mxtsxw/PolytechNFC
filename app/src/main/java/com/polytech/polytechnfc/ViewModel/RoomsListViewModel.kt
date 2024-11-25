package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.Room
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomsListViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel(){

        private val _roomsState = MutableStateFlow<List<Room>>(emptyList())
        val roomsState: StateFlow<List<Room>> = _roomsState

        init {
            fetchRooms()
        }

        private fun fetchRooms(){
            viewModelScope.launch {
                val rooms = firestoreService.getRooms()
                _roomsState.value = rooms
            }
        }

}