package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var roomAdapter: RoomAdapter
    private val db = FirebaseFirestore.getInstance()
    private val roomList = mutableListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerDorms)
        recyclerView.layoutManager = LinearLayoutManager(this)
        roomAdapter = RoomAdapter(roomList) { selectedRoom ->
            val intent = Intent(this, RentDetails::class.java)
            intent.putExtra("roomAddress", selectedRoom.roomAddress)
            intent.putExtra("roomPrice", selectedRoom.roomPrice)
            intent.putExtra("roomSlot", selectedRoom.roomSlot)
            intent.putExtra("roomType", selectedRoom.roomType)
            startActivity(intent)
        }
        recyclerView.adapter = roomAdapter

        fetchRooms()

        // Profile Button
        val profileImage = findViewById<ImageView>(R.id.profile_image)
        profileImage.setOnClickListener {
            val intent = Intent(this, Profile_Student::class.java)
            startActivity(intent)
        }

        // Search Button
        val searchIcon: ImageView = findViewById(R.id.search_icon)
        searchIcon.setOnClickListener {
            val intent = Intent(this, SearchView::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRooms() {
        db.collection("rooms").get()
            .addOnSuccessListener { documents ->
                roomList.clear()
                for (document in documents) {
                    val room = document.toObject(Room::class.java)
                    roomList.add(room)
                }
                roomAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
