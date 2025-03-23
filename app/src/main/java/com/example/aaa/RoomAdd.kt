package com.example.aaa

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RoomAdd : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private var imageUris = mutableListOf<Uri?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_add)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnUploadImage = findViewById<Button>(R.id.btnUploadImage)
        val imagePreview1 = findViewById<ImageView>(R.id.imagePreview1)
        val imagePreview2 = findViewById<ImageView>(R.id.imagePreview2)
        val imagePreview3 = findViewById<ImageView>(R.id.imagePreview3)

        btnUploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
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
