package com.example.aaa

data class Room (
    var id: String = "",
    val roomType: String = "",
    val roomSlot: Int = 0,
    val roomPrice: Double = 0.0,
    val roomAddress: String = "",
    val utilities: List<String> = emptyList(),
    val imageUrl: String = "" // Placeholder for image if needed later
)