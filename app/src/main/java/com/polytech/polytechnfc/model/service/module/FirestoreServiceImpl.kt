package com.polytech.polytechnfc.model.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.snap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.polytech.polytechnfc.model.BadgeInfo
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.polytech.polytechnfc.model.Record
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


class FirestoreServiceImpl : FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val recordsCollection = firestore.collection("records")
    private val badgesCollection = firestore.collection("badges")
    private val roomsCollection = firestore.collection("rooms")
    private val rolesCollection = firestore.collection("roles")

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getRecords(): List<Record> {
        return try {
            val snapshot = recordsCollection.get().await()
            Log.d("FirestoreServiceImpl", "Raw snapshot size: ${snapshot.size()}")
            val records = snapshot.documents.mapNotNull { document ->
                val timestamp = document.getTimestamp("timestamp")
                val uid = document.getString("uid")
                Log.i("FirestoreServiceImpl", "Timestamp: $timestamp")
                if (timestamp != null) {
                    val adjustedTimestamp = Date(timestamp.seconds * 1000 + 60 * 60 * 1000)
                    Record(
                        id = document.id,
                        uid = uid ?: "",
                        //timestamp = Date(timestamp.seconds * 1000)
                        timestamp = adjustedTimestamp
                    )

                } else {
                    Log.w(
                        "FirestoreServiceImpl",
                        "Document ignored, missing timestamp: ${document.id}"
                    )
                    null
                }

            }
            // Log des résultats pour vérifier
            Log.d("FirestoreServiceImpl", "Fetched records: $records")
            records
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching records", e)
            emptyList()

        }

    }

    override suspend fun getBadgeInfos(): List<BadgeInfo> {
        return try {
            val snapshot = badgesCollection.get().await()
            snapshot.documents.mapNotNull {document ->
                val name = document.getString("name")
                val uid = document.getString("uid")
                if(name != null && uid != null){
                    BadgeInfo(
                        id = document.id,
                        name = name,
                        uid = uid
                    )
                } else {
                    Log.w("FirestoreServiceImpl", "Document ignored, missing name or uid: ${document.id}")
                    null
                }

            }
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching badge ids", e)
            emptyList()
        }
    }

    override suspend fun getRooms(): List<Room> {
        return try {
            val snapshot = roomsCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                val name = document.getString("name")
                if (name != null) {
                    Room(
                        id = document.id,
                        name = name
                    )
                } else {
                    Log.w("FirestoreServiceImpl", "Document ignored, missing name: ${document.id}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching rooms", e)
            emptyList()
        }
    }

    override suspend fun getRoles(): List<Role> {
        return try {
            val snapshot = rolesCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                val name = document.getString("name")
                if (name != null) {
                    Role(
                        id = document.id,
                        name = name
                    )
                } else {
                    Log.w("FirestoreServiceImpl", "Document ignored, missing name: ${document.id}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching roles", e)
            emptyList()
        }
    }

    override suspend fun addAccess(accessData: Map<String, Any>) {
        withContext(Dispatchers.IO) {
            try {
                firestore.collection("accesses").add(accessData).await()
            } catch (e: Exception) {
                Log.e("FirestoreServiceImpl", "Error adding access", e)
            }
        }


}

}