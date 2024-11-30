package com.polytech.polytechnfc.model.service

import com.polytech.polytechnfc.model.BadgeInfo
import com.polytech.polytechnfc.model.Record
import com.polytech.polytechnfc.model.Role
import com.polytech.polytechnfc.model.Room
import com.polytech.polytechnfc.model.UserBadge

interface FirestoreService {
    suspend fun getRecords(): List<Record>
    suspend fun getBadgeInfos(): List<BadgeInfo>
    suspend fun getRooms(): List<Room>
    suspend fun getRoles(): List<Role>
    suspend fun addAccess(accessData: Map<String, Any>)
    suspend fun getUsers(): List<UserBadge>
    suspend fun updateUser(user: UserBadge)
}


