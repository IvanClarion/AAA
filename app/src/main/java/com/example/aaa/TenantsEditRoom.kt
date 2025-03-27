package com.example.aaa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class TenantsEditRoom : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var roomId: String? = null
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tenants_edit_room)

        firestore = FirebaseFirestore.getInstance()

        // Initialize UI elements
        val backButton = findViewById<ImageView>(R.id.backButton)
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSubmitRoom = findViewById<Button>(R.id.btnSubmitRoom)
        val deleteRoom = findViewById<Button>(R.id.deleteRoom)

        val etRoomType = findViewById<EditText>(R.id.etRoomType)
        val etRoomSlot = findViewById<EditText>(R.id.etRoomSlot)
        val etRoomPrice = findViewById<EditText>(R.id.etRoomPrice)
        val etRoomAddress = findViewById<EditText>(R.id.etRoomAddress)
        val imagePreview = findViewById<ImageView>(R.id.imagePreview1)

        // Get Room Details from Intent
        val extras = intent.extras
        if (extras != null) {
            roomId = extras.getString("roomId", "")
            val roomType = extras.getString("roomType", "")
            val roomSlot = extras.getString("roomSlot", "")
            val roomPrice = extras.getString("roomPrice", "")
            val roomAddress = extras.getString("roomAddress", "")
            val imageUrl = extras.getString("imageUrl", "")

            Log.d("TenantsEditRoom", "Received Intent Data - ID: $roomId, Type: $roomType, Slot: $roomSlot, Price: $roomPrice, Address: $roomAddress")

            etRoomType.setText(roomType)
            etRoomSlot.setText(roomSlot) // ✅ Room Slot Fixed
            etRoomPrice.setText(roomPrice) // ✅ Room Price Fixed
            etRoomAddress.setText(roomAddress)

            // Load Image if Available
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(this).load(imageUrl).into(imagePreview)
            } else {
                imagePreview.setImageResource(R.drawable.sampleimage1) // Default image
            }
        }

        // Handle Back Button
        backButton.setOnClickListener { finish() }

        // Handle Image Upload
        btnUploadImage.setOnClickListener { selectImage() }

        // Handle Save Room Changes
        btnSubmitRoom.setOnClickListener {
            saveRoomChanges(
                etRoomType.text.toString(),
                etRoomSlot.text.toString(),
                etRoomPrice.text.toString(),
                etRoomAddress.text.toString()
            )
        }

        // Handle Delete Room
        deleteRoom.setOnClickListener { deleteRoom() }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    private fun saveRoomChanges(roomType: String, roomSlot: String, roomPrice: String, roomAddress: String) {
        if (roomId.isNullOrEmpty()) {
            Toast.makeText(this, "Error: Room ID is missing", Toast.LENGTH_SHORT).show()
            return
        }

        val roomData = hashMapOf(
            "roomType" to roomType,
            "roomSlot" to roomSlot,
            "roomPrice" to roomPrice,
            "roomAddress" to roomAddress
        )

        firestore.collection("rooms").document(roomId!!)
            .set(roomData, com.google.firebase.firestore.SetOptions.merge()) // ✅ Prevents data loss
            .addOnSuccessListener {
                Toast.makeText(this, "Room details updated!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error updating room!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteRoom() {
        if (roomId.isNullOrEmpty()) {
            Toast.makeText(this, "Error: Room ID is missing", Toast.LENGTH_SHORT).show()
            return
        }

        firestore.collection("rooms").document(roomId!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Room deleted!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error deleting room!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            findViewById<ImageView>(R.id.imagePreview1).setImageURI(selectedImageUri)
        }
    }
}
