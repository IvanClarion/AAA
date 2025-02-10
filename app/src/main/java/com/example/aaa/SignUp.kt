package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val signUp: TextView = findViewById(R.id.SignIn)
        signUp.setOnClickListener {
            val intent = Intent(this, Login_Tenants::class.java)
            startActivity(intent)
        }
        val explicitButton = findViewById<ImageButton>(R.id.backbutton)
        explicitButton.setOnClickListener {
            val explicitIntent = Intent(this, Login_Tenants::class.java)
            startActivity(explicitIntent)
        }
    }
}