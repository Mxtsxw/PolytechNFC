package com.polytech.polytechnfc.model.service

import com.polytech.polytechnfc.model.Record
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.Room

interface FirestoreService {
    suspend fun getRecords(): List<Record>
    suspend fun getBadgeIds(): List<String>
    suspend fun getRooms(): List<Room>
    suspend fun getRoles(): List<Role>
}


