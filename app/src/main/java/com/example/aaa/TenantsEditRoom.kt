package com.example.aaa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.aaa.R

class TenantsEditRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenants_edit_room)

        // Initialize UI elements
        val backButton = findViewById<ImageView>(R.id.backButton)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSubmitRoom = findViewById<Button>(R.id.btnSubmitRoom)
        val deleteRoom = findViewById<Button>(R.id.deleteRoom)

        val etRoomType = findViewById<EditText>(R.id.etRoomType)
        val etRoomSlot = findViewById<EditText>(R.id.etRoomSlot)
        val etRoomPrice = findViewById<EditText>(R.id.etRoomPrice)
        val etRoomAddress = findViewById<EditText>(R.id.etRoomAddress)

        val cbWifi = findViewById<CheckBox>(R.id.cb_wifi)
        val cbSeparateBathroom = findViewById<CheckBox>(R.id.cb_separate_bathroom)
        val cbBedSpacer = findViewById<CheckBox>(R.id.cb_bed_spacer)
        val cbSingleBed = findViewById<CheckBox>(R.id.cb_single_bed)

        // Handle Back Button
        backButton.setOnClickListener {
            finish() // Close activity and go back
        }

        // Handle Image Upload
        btnUploadImage.setOnClickListener {
            selectImage()
        }

        // Handle Save Room Changes
        btnSubmitRoom.setOnClickListener {
            saveRoomChanges(
                etRoomType.text.toString(),
                etRoomSlot.text.toString(),
                etRoomPrice.text.toString(),
                etRoomAddress.text.toString(),
                cbWifi.isChecked,
                cbSeparateBathroom.isChecked,
                cbBedSpacer.isChecked,
                cbSingleBed.isChecked
            )
        }

        // Handle Delete Room
        deleteRoom.setOnClickListener {
            deleteRoom()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    private fun saveRoomChanges(
        roomType: String, roomSlot: String, roomPrice: String, roomAddress: String,
        wifi: Boolean, separateBathroom: Boolean, bedSpacer: Boolean, singleBed: Boolean
    ) {
        // Logic to save the room data (e.g., send to database)
        Toast.makeText(this, "Room details updated!", Toast.LENGTH_SHORT).show()
    }

    private fun deleteRoom() {
        // Logic to delete the room (e.g., remove from database)
        Toast.makeText(this, "Room deleted!", Toast.LENGTH_SHORT).show()
        finish() // Close activity after deleting
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            findViewById<ImageView>(R.id.imagePreview1).setImageURI(selectedImageUri)
        }
    }
}
