package com.example.aaa

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RentDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rent_details)

        // Ensure window insets are applied properly
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Find the "Book Now" button
        val bookNowButton: Button = findViewById(R.id.bookNowButton)

        // Set click listener to show the modal
        bookNowButton.setOnClickListener {
            showSuccessModal()
        }
    }

    private fun showSuccessModal() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view: View = inflater.inflate(R.layout.modal_success, null)
        builder.setView(view)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        // Find the "Continue" button inside the modal and set its click listener
        val continueButton: Button = view.findViewById(R.id.ModalContinue)
        continueButton.setOnClickListener {
            dialog.dismiss() // Close modal when "Continue" is clicked
        }
        val profileImage = findViewById<ImageView>(R.id.btnBack)
        profileImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
