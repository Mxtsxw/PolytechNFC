package com.polytech.polytechnfc.model

data class UserBadge(
    val id: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val role: Role?,
    val badge: BadgeInfo?

)
