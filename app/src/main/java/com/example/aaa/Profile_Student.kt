package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile_Student : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var editButton: ImageButton
    private lateinit var saveButton: Button // Add save button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        editButton = findViewById(R.id.editButton)
        saveButton = findViewById(R.id.profile_save) // Initialize save button

        // Initially disable the EditText fields and the save button
        nameEditText.isEnabled = false
        emailEditText.isEnabled = false
        saveButton.isEnabled = false // Disable save button initially
        saveButton.visibility = View.GONE // Hide save button initially


        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Set click listener for the edit button
        editButton.setOnClickListener {
            onEditButtonClicked()
        }

        // Set click listener for the save button
        saveButton.setOnClickListener {
            onSaveButtonClicked()
        }
    }

    fun onEditButtonClicked() {
        // Toggle the enabled state of the EditText fields
        nameEditText.isEnabled = !nameEditText.isEnabled
        emailEditText.isEnabled = !emailEditText.isEnabled

        // Toggle visibility and enabled state of save button
        saveButton.visibility = if (nameEditText.isEnabled) View.VISIBLE else View.GONE
        saveButton.isEnabled = nameEditText.isEnabled

        // No longer changing the edit button icon

    }

    fun onSaveButtonClicked() {
        // Here you would save the data from the EditText fields
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()

        //For now, let's just show a toast
        Toast.makeText(this, "Saving Profile...\nName: $name\nEmail: $email", Toast.LENGTH_SHORT).show()

        // Disable EditText fields and hide save button after saving
        nameEditText.isEnabled = false
        emailEditText.isEnabled = false
        saveButton.visibility = View.GONE
        saveButton.isEnabled = false

    }
}
