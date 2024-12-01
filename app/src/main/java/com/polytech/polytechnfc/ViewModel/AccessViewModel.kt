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
import com.google.firebase.firestore.FirebaseFirestore
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.Room
import com.polytech.polytechnfc.model.service.FirestoreService
import com.polytech.polytechnfc.model.service.FirestoreServiceImpl
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

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
    var roles by mutableStateOf<List<Role>>(emptyList())

    init {
        viewModelScope.launch {
            try{
            rooms = firestoreService.getRooms()
            Log.d("AccessViewModel", "Rooms retrieved: $rooms")
            roles = firestoreService.getRoles()
            Log.d("AccessViewModel", "Roles retrieved: $roles")
            }
            catch(e: Exception){
                Log.e("AccessViewModel", "Error fetching data: ${e.message}")
            }
        }
    }

    fun createAccess(onSuccess: () -> Unit, onError: (String) -> Unit){
        viewModelScope.launch{

            try {
                val firestore = FirebaseFirestore.getInstance()

                val selectedRole = roles.find { it.label == role }
                val selectedRoom = rooms.find { it.name == room }

                if (selectedRole == null || selectedRoom == null) {
                    onError("Le rôle ou la salle sélectionnée est introuvable.")
                    return@launch
                }
                val roleRef = firestore.collection("roles").document(selectedRole.id)
                val roomRef = firestore.collection("rooms").document(selectedRoom.id)
                val accessData = mapOf(
                    "role" to roleRef,
                    "room" to roomRef,
                    "start" to LocalDateTime.of(startDate, startTime).atZone(ZoneId.systemDefault()).toString(),
                    "end" to LocalDateTime.of(endDate, endTime).atZone(ZoneId.systemDefault()).toString()
                )

                firestoreService.addAccess(accessData)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Une erreur est survenue lors de la création de l'accès.")
            }
        }

    }

}