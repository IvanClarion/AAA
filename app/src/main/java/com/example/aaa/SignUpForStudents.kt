package com.example.aaa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SignUpForStudents : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_for_students)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val explicitButton = findViewById<ImageButton>(R.id.backbuttonStudents)
        explicitButton.setOnClickListener {
            val explicitIntent = Intent(this, LoginPage::class.java)
            startActivity(explicitIntent)
        }
        val signUp: TextView = findViewById(R.id.SignInForStudents)
        signUp.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        val button = findViewById<Button>(R.id.loginbuttonstudents)
        button.setOnClickListener {
            val dialog = SignUpDialogFragment()
            dialog.show(supportFragmentManager, "modal")
        }

    }
}