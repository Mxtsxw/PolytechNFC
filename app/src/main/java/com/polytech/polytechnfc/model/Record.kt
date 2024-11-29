package com.polytech.polytechnfc.model

import com.google.firebase.Timestamp
import java.util.Date

data class Record(
    val id: String = "",
    val uid: String = "",
    val timestamp: Date,
    val granted: Boolean = false,
    val user: String? = null,
    val room: Room? = null
)
