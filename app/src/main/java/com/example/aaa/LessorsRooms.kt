package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class LessorsRooms : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val firestore = FirebaseFirestore.getInstance()
    private val roomList = mutableListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessors_rooms)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.roomsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Floating Action Button to Add Room
        val addRoom = findViewById<ImageButton>(R.id.addRoomFab)
        addRoom?.setOnClickListener {
            startActivity(Intent(this, RoomAdd::class.java))
        }
        addRoom.bringToFront() // Ensure the FAB stays clickable

        // Back Button Functionality
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton?.setOnClickListener {
            finish() // Closes this activity and returns to the previous one
        }

        fetchRoomsFromFirestore()
    }

    override fun onResume() {
        super.onResume()
        fetchRoomsFromFirestore()
    }

    private fun fetchRoomsFromFirestore() {
        firestore.collection("rooms")
            .get()
            .addOnSuccessListener { documents ->
                roomList.clear()
                for (document in documents) {
                    val room = document.toObject(Room::class.java).apply {
                        id = document.id // Store Firestore document ID
                    }
                    roomList.add(room)
                }
                recyclerView.adapter = RoomAdapter(roomList) { room ->
                    val intent = Intent(this, TenantsEditRoom::class.java)
                    intent.putExtra("roomId", room.id)
                    intent.putExtra("roomType", room.roomType)
                    intent.putExtra("roomSlot", room.roomSlot)
                    intent.putExtra("roomPrice", room.roomPrice)
                    intent.putExtra("roomAddress", room.roomAddress)
                    intent.putExtra("imageUrl", room.imageUrl)
                    startActivity(intent)
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching rooms", e)
            }
    }
}
