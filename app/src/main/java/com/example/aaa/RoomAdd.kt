package com.example.aaa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RoomAdd : AppCompatActivity() {

    companion object {
        var roomType: String = ""
        var roomSlot: String = ""
        var roomPrice: String = ""
        var roomAddress: String = ""
    }

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_add)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to form fields
        val etRoomType = findViewById<EditText>(R.id.etRoomType)
        val etRoomSlot = findViewById<EditText>(R.id.etRoomSlot)
        val etRoomPrice = findViewById<EditText>(R.id.etRoomPrice)
        val etRoomAddress = findViewById<EditText>(R.id.etRoomAddress)
        val btnSubmitRoom = findViewById<Button>(R.id.btnSubmitRoom)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val backButton = findViewById<ImageView>(R.id.backButton)

        // Set stored values
        etRoomType.setText(roomType)
        etRoomSlot.setText(roomSlot)
        etRoomPrice.setText(roomPrice)
        etRoomAddress.setText(roomAddress)

        // Open file picker when image upload button is clicked
        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Reset form and go back when submit button is clicked
        btnSubmitRoom.setOnClickListener {
            etRoomType.setText("")
            etRoomSlot.setText("")
            etRoomPrice.setText("")
            etRoomAddress.setText("")
            roomType = ""
            roomSlot = ""
            roomPrice = ""
            roomAddress = ""

            finish() // Go back like the back button
        }

        // Handle back button click
        backButton.setOnClickListener {
            finish() // Close this activity and go back
        }
    }

    // Handle the selected image result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            // You can display the imageUri in an ImageView if needed
        }
    }
}
