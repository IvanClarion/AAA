package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Categorization : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_categorization)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val explicitButton1 = findViewById<Button>(R.id.signInasStudent)
        explicitButton1.setOnClickListener {
            val explicitIntent = Intent(this, LoginPage::class.java)
            startActivity(explicitIntent)
        }

        val explicitButton2 = findViewById<Button>(R.id.signInasLessor)
        explicitButton2.setOnClickListener {
            val explicitIntent = Intent(this, Login_Tenants::class.java)
            startActivity(explicitIntent)
        }
    }
}
