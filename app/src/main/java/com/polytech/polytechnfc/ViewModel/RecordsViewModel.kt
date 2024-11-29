package com.polytech.polytechnfc.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.polytech.polytechnfc.model.Record

@RequiresApi(Build.VERSION_CODES.O)
class RecordsViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {
    private val _recordsState = MutableStateFlow<List<Record>>(emptyList())
    val recordsState: StateFlow<List<Record>> = _recordsState

    init {
        fetchRecords()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRecords() {
        viewModelScope.launch {
            val records = firestoreService.getRecords()
            _recordsState.value = records
        }


    }
}