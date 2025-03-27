package com.example.aaa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RoomAdd : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var imageUris = mutableListOf<Uri?>()
    private val db = FirebaseFirestore.getInstance() // Firestore instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_add)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI Elements
        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val btnSubmitRoom = findViewById<Button>(R.id.btnSubmitRoom)
        val etRoomType = findViewById<EditText>(R.id.etRoomType)
        val etRoomSlot = findViewById<EditText>(R.id.etRoomSlot)
        val etRoomPrice = findViewById<EditText>(R.id.etRoomPrice)
        val etRoomAddress = findViewById<EditText>(R.id.etRoomAddress)

        val cbWifi = findViewById<CheckBox>(R.id.cb_wifi)
        val cbBathroom = findViewById<CheckBox>(R.id.cb_separate_bathroom)
        val cbBedSpacer = findViewById<CheckBox>(R.id.cb_bed_spacer)
        val cbSingleBed = findViewById<CheckBox>(R.id.cb_single_bed)

        val imagePreview1 = findViewById<ImageView>(R.id.imagePreview1)
        val imagePreview2 = findViewById<ImageView>(R.id.imagePreview2)
        val imagePreview3 = findViewById<ImageView>(R.id.imagePreview3)

        // Image Upload Click
        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Submit Room Data (Ignoring Images)
        btnSubmitRoom.setOnClickListener {
            val roomType = etRoomType.text.toString().trim()
            val roomSlot = etRoomSlot.text.toString().trim()
            val roomPrice = etRoomPrice.text.toString().trim()
            val roomAddress = etRoomAddress.text.toString().trim()
            val utilities = mutableListOf<String>()

            if (cbWifi.isChecked) utilities.add("WIFI")
            if (cbBathroom.isChecked) utilities.add("Separate Bathroom")
            if (cbBedSpacer.isChecked) utilities.add("Bed Spacer")
            if (cbSingleBed.isChecked) utilities.add("Single Bed")

            if (roomType.isEmpty() || roomSlot.isEmpty() || roomPrice.isEmpty() || roomAddress.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val roomData = hashMapOf(
                "roomType" to roomType,
                "roomSlot" to roomSlot.toInt(),
                "roomPrice" to roomPrice.toDouble(),
                "roomAddress" to roomAddress,
                "utilities" to utilities
            )

            db.collection("rooms")
                .add(roomData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Room added successfully!", Toast.LENGTH_SHORT).show()
                    // Reset form after successful submission
                    etRoomType.text.clear()
                    etRoomSlot.text.clear()
                    etRoomPrice.text.clear()
                    etRoomAddress.text.clear()

                    cbWifi.isChecked = false
                    cbBathroom.isChecked = false
                    cbBedSpacer.isChecked = false
                    cbSingleBed.isChecked = false

                    imageUris.clear()
                    imagePreview1.setImageDrawable(null)
                    imagePreview2.setImageDrawable(null)
                    imagePreview3.setImageDrawable(null)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add room!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUris.clear()

            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                for (i in 0 until minOf(count, 3)) {
                    imageUris.add(data.clipData!!.getItemAt(i).uri)
                }
            } else if (data?.data != null) {
                imageUris.add(data.data)
            }

            // Display images in the preview boxes
            val previewBoxes = listOf(R.id.imagePreview1, R.id.imagePreview2, R.id.imagePreview3)
            for (i in imageUris.indices) {
                findViewById<ImageView>(previewBoxes[i]).setImageURI(imageUris[i])
            }
        }
    }
}
