package com.polytech.polytechnfc.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.Reader
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadersViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {

    private val _readersState = MutableStateFlow<List<Reader>>(emptyList())
    val readersState: StateFlow<List<Reader>> = _readersState

    init {
        fetchReaderIds()
    }

    private fun fetchReaderIds() {
        viewModelScope.launch {
            val readers = firestoreService.getReaders()
            _readersState.value = readers
        }
    }
}