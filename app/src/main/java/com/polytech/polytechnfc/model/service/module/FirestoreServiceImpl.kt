package com.polytech.polytechnfc.model.service

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.polytech.polytechnfc.model.BadgeInfo
import com.polytech.polytechnfc.model.Reader
import kotlinx.coroutines.tasks.await
import com.polytech.polytechnfc.model.Record
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import com.polytech.polytechnfc.model.UserBadge as UserBadge


class FirestoreServiceImpl : FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    val recordsCollection = firestore.collection("records")
    private val badgesCollection = firestore.collection("badges")
    private val roomsCollection = firestore.collection("rooms")
    private val rolesCollection = firestore.collection("roles")
    private val usersCollection = firestore.collection("users")

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getRecords(): List<Record> {
        return try {
            val snapshot = recordsCollection
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .await()
            Log.d("FirestoreServiceImpl", "Raw snapshot size: ${snapshot.size()}")
            val records = snapshot.documents.mapNotNull { document ->
                val timestamp = document.getTimestamp("timestamp")
                val uid = document.getString("uid")
                val granted = document.getBoolean("granted")
                Log.i("FirestoreServiceImpl", "Timestamp: $timestamp")
                if (timestamp != null) {
                    Record(
                        id = document.id,
                        uid = uid ?: "",
                        timestamp = Date(timestamp.seconds * 1000),
                        granted = granted ?: false,
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
                val reader = document.getDocumentReference("reader")
                if (name != null) {
                    Room(
                        id = document.id,
                        name = name,
                        reader = reader?.let { fetchReader(it) }
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
                val label = document.getString("label")
                if (label != null) {
                    Role(
                        id = document.id,
                        label = label
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

    suspend fun getReaders(): List<Reader> {
        return try {
            val snapshot = firestore.collection("readers").get().await()
            snapshot.documents.mapNotNull { document ->
                val name = document.getString("name")
                val room = document.getDocumentReference("room")
                if (name != null) {
                    Reader(
                        id = document.id,
                        name = name,
                        room = room?.let { fetchRoom(it) }
                    )
                } else {
                    Log.w("FirestoreServiceImpl", "Document ignored, missing name: ${document.id}")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching readers", e)
            emptyList()
        }
    }

    override suspend fun getUsers(): List<UserBadge> {
        return try {
            val snapshot = usersCollection.get().await()
            snapshot.documents.mapNotNull { document ->
                val email = document.getString("email")
                val firstname = document.getString("firstname")
                val lastname = document.getString("lastname")
                val role = document.getDocumentReference("role")
                val badge = document.getDocumentReference("badge")

            if(email != null && firstname != null && lastname != null){
                val roleData = role?.let { fetchRole(it) }
                val badgeData = badge?.let { fetchBadge(it) }
                UserBadge(
                    id = document.id,
                    email = email,
                    firstname = firstname,
                    lastname = lastname,
                    role = roleData?.let {Role(id = it.id, label = it.label)},
                    badge = badgeData?.let { BadgeInfo(id = it.id, name = it.name, uid = it.uid) }
                )
            } else {
                Log.w("FirestoreServiceImpl", "Document ignored, missing email, firstname, lastname, role or badge: ${document.id}")
                null
            }
        }
        }
        catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching users", e)
            emptyList()
        }
    }

    /**
     * Fetch a role from a document reference
     */
    private suspend fun fetchRole(roleRef: DocumentReference): Role? {
        return try {
            val snapshot = roleRef.get().await()
            snapshot.toObject(Role::class.java)?.copy(id = snapshot.id)
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching role: ${roleRef.id}", e)
            null
        }
    }

    /**
     * Fetch a badge from a document reference
     */
    private suspend fun fetchBadge(badgeRef: DocumentReference): BadgeInfo? {
        return try {
            val snapshot = badgeRef.get().await()
            snapshot.toObject(BadgeInfo::class.java)?.copy(id = snapshot.id)
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching badge: ${badgeRef.id}", e)
            null
        }
    }

    /**
     * Fetch a reader from a document reference
     */
    private suspend fun fetchReader(readerRef: DocumentReference): Reader? {
        return try {
            val snapshot = readerRef.get().await()
            snapshot.toObject(Reader::class.java)?.copy(id = snapshot.id)
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching reader: ${readerRef.id}", e)
            null
        }
    }

    /**
     * Fetch a room from a document reference
     */
    private suspend fun fetchRoom(roomRef: DocumentReference): Room? {
        return try {
            val snapshot = roomRef.get().await()
            snapshot.toObject(Room::class.java)?.copy(id = snapshot.id)
        } catch (e: Exception) {
            Log.e("FirestoreServiceImpl", "Error fetching room: ${roomRef.id}", e)
            null
        }
    }

    override suspend fun updateUser(user: UserBadge) {
        withContext(Dispatchers.IO) {
            try {
                // Map des données à mettre à jour
                val userData = mutableMapOf<String, Any>()

                //Ajout uniquemenet des champs modifiés
                user.firstname?.let { userData["firstname"] = it }
                user.lastname?.let { userData["lastname"] = it }

                // Vérifie si un nouveau rôle est sélectionné
                user.role?.id?.takeIf { it.isNotBlank() }?.let { roleId ->
                    val roleRef = rolesCollection.document(roleId) // Référence au nouveau rôle
                    userData["role"] = roleRef // Ajoute la référence au map
                    Log.d("FirestoreServiceImpl", "Role updated with reference: ${roleRef.path}")
                }

                // Vérifie si un nouveau badge est sélectionné
                user.badge?.id?.takeIf { it.isNotBlank() }?.let { badgeId ->
                    val badgeRef = badgesCollection.document(badgeId)
                    userData["badge"] = badgeRef
                    Log.d("FirestoreServiceImpl", "Badge updated with reference: ${badgeRef.path}")
                }

                //Mise à jour
                if (userData.isNotEmpty()) {
                    usersCollection.document(user.id).update(userData).await()
                    Log.d("FirestoreServiceImpl", "User ${user.id} updated successfully")
                } else {
                    Log.d("FirestoreServiceImpl", "No fields to update for user ${user.id}")
                }





            } catch (e: Exception) {
                Log.e("FirestoreServiceImpl", "Error updating user: ${user.id}", e)
            }
        }

    }
}
