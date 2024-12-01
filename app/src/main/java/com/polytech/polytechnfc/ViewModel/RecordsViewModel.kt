package com.polytech.polytechnfc.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.polytech.polytechnfc.model.Record
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class RecordsViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel() {
    private val _recordsState = MutableStateFlow<List<Record>>(emptyList())
    val recordsState: StateFlow<List<Record>> = _recordsState

    private var listenerRegistration: ListenerRegistration? = null

    init {
        startListeningToRecords()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startListeningToRecords() {
        listenerRegistration = firestoreService.recordsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("RecordsViewModel", "Error listening for updates", exception)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val records = snapshot.documents.mapNotNull { document ->
                        val timestamp = document.getTimestamp("timestamp")
                        val uid = document.getString("uid")
                        val granted = document.getBoolean("granted")

                        if (timestamp != null) {
                            Record(
                                id = document.id,
                                uid = uid ?: "",
                                timestamp = Date(timestamp.seconds * 1000),
                                granted = granted ?: false,
                            )
                        } else {
                            null
                        }
                    }
                    _recordsState.value = records
                    Log.d("RecordsViewModel", "Records updated: $records")
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        // Remove Firestore listener when ViewModel is cleared
        listenerRegistration?.remove()
    }
}