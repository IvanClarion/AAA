package com.example.aaa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        val explicitButton = findViewById<Button>(R.id.loginbuttonstudents)
        explicitButton.setOnClickListener {
            val explicitIntent = Intent(this, StudentPlatform::class.java)
            startActivity(explicitIntent)
        }
        val signUp: TextView = findViewById(R.id.SignUpStudents)
        signUp.setOnClickListener {
            val intent = Intent(this, SignUpForStudents::class.java)
            startActivity(intent)
        }
    }
}