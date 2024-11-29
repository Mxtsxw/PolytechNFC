package com.polytech.polytechnfc.ViewModel


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polytech.polytechnfc.model.Room
import com.polytech.polytechnfc.model.service.FirestoreService
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class AccessViewModel(
    private val firestoreService: FirestoreServiceImpl
) : ViewModel(){

    var role by mutableStateOf("")
    var room by mutableStateOf("")
    var startDate by mutableStateOf(LocalDate.now())
    var startTime by mutableStateOf(LocalTime.of(8,0))
    var endDate by mutableStateOf(LocalDate.now())
    var endTime by mutableStateOf(LocalTime.of(18,0))

    var rooms by mutableStateOf<List<Room>>(emptyList())

    init {
        viewModelScope.launch {
            try{
            rooms = firestoreService.getRooms()
            Log.d("AccessViewModel", "Rooms retrieved: $rooms")
            }
            catch(e: Exception){
                Log.e("AccessViewModel", "Error fetching rooms: ${e.message}")
            }
        }
    }

    fun createAccess(onSuccess: () -> Unit, onError: (String) -> Unit){
        viewModelScope.launch{
            try{
                val accessData = mapOf(
                    "id" to role,
                    "name" to role,
                    "permissions" to listOf(
                        mapOf(
                            "roomid" to room,
                            "access_time" to mapOf(
                                "start" to LocalDateTime.of(startDate, startTime).toString(),
                                "end" to LocalDateTime.of(endDate, endTime).toString()
                            )
                        )
                    )
                )
                firestoreService.addAccess(accessData)
                onSuccess()
            }
            catch(e: Exception){
                onError(e.message ?: "An error occurred")
            }
        }

    }

}