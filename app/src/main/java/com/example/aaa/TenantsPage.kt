package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TenantsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tenants_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find profile image and set click listener
        val profileImage = findViewById<ImageView>(R.id.profile_image)
        profileImage.setOnClickListener {
            val intent = Intent(this, TenantsAccount::class.java)
            startActivity(intent)
        }

        // Find Residents image and set click listener (Fixed issue here)
        val residents = findViewById<LinearLayout>(R.id.total_residents)
        residents.setOnClickListener {
            val intent = Intent(this, ResidentsTenants::class.java)
            startActivity(intent)
        }
        val rooms = findViewById<LinearLayout>(R.id.Rooms)
        rooms.setOnClickListener {
            val intent = Intent(this, LessorsRooms::class.java)
            startActivity(intent)
        }
    }
}
