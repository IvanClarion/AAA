package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aaa.TenantsEditRoom

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

        // Back Button Functionality
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton?.setOnClickListener {
            finish() // Closes this activity and returns to the previous one
        }

        // Floating Action Button to Add Room
        val addRoom = findViewById<ImageButton>(R.id.addRoomFab)
        addRoom?.setOnClickListener {
            startActivity(Intent(this, RoomAdd::class.java))
        }

        // Room Card Click to Navigate to TenantsEditRoom
        val roomCard = findViewById<LinearLayout>(R.id.roomCardLayout)
        roomCard?.setOnClickListener {
            val intent = Intent(this, TenantsEditRoom::class.java)
            startActivity(intent)
        }
    }
}
