package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadersViewModel (
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {

    private val _readerIdsState = MutableStateFlow<List<String>>(emptyList())
    val readerIdsState: StateFlow<List<String>> = _readerIdsState

    init {
        fetchReaderIds()
    }

    private fun fetchReaderIds() {
        viewModelScope.launch {
            val readerIds = firestoreService.getReaderIds()
            _readerIdsState.value = readerIds
        }
    }
}