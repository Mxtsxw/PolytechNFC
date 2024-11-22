package com.polytech.polytechnfc.service

import com.polytech.polytechnfc.model.Record

interface FirestoreService {
    suspend fun getRecords(): List<Record>
}
