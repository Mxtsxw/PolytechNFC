package com.polytech.polytechnfc.model.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.snap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.polytech.polytechnfc.service.FirestoreService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.polytech.polytechnfc.model.Record
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


class FirestoreServiceImpl : FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val recordsCollection = firestore.collection("records")

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getRecords() : List<Record> {
        return try {
            val snapshot = recordsCollection.get().await()
            Log.d("FirestoreServiceImpl", "Raw snapshot size: ${snapshot.size()}")
            val records = snapshot.documents.mapNotNull { document ->
                val timestamp = document.getTimestamp("Timestamp")
                if(timestamp != null) {
                    val date = timestamp.toDate()
                    Record(
                        id = document.id,
                        //timestamp = timestamp
                        timestamp = date
                    )

                } else {
                    Log.w("FirestoreServiceImpl", "Document ignored, missing timestamp: ${document.id}")
                    null
                }

            }
            // Log des résultats pour vérifier
            Log.d("FirestoreServiceImpl", "Fetched records: $records")
            records
        } catch(e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching records", e)
            emptyList()

        }

    }
}