package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LessorsRooms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lessors_rooms)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ensure backButton exists in the layout before setting a click listener
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton?.setOnClickListener {
            finish() // Properly closes this activity and returns to the previous one
        }

        // Fix incorrect usage of backButton for navigating to RoomAdd
        val addRoom = findViewById<ImageButton>(R.id.addRoomFab)
        addRoom?.setOnClickListener {
            startActivity(Intent(this, RoomAdd::class.java))
        }
    }
}
