package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dorm1: LinearLayout = findViewById(R.id.Dorm1)

        dorm1.setOnClickListener {
            val intent = Intent(this, RentDetails::class.java)
            startActivity(intent)
        }
        val profileImage = findViewById<ImageView>(R.id.profile_image)
        profileImage.setOnClickListener {
            val intent = Intent(this, Profile_Student::class.java)
            startActivity(intent)
        }
        val searchIcon: ImageView = findViewById(R.id.search_icon)

        searchIcon.setOnClickListener {
            // Intent to navigate to the other activity
            val intent = Intent(this, SearchView::class.java) // Replace YourTargetActivity
            startActivity(intent)
        }
    }
}