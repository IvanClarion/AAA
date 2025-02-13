package com.example.aaa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth & Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageButton>(R.id.backbutton)
        backButton.setOnClickListener {
            startActivity(Intent(this, Login_Tenants::class.java))
        }

        val signInText = findViewById<TextView>(R.id.SignIn)
        signInText.setOnClickListener {
            startActivity(Intent(this, Login_Tenants::class.java))
        }

        val signUpButton = findViewById<Button>(R.id.btn_signup_lessor)
        signUpButton.setOnClickListener {
            fetchAndUpdateLessorId { lessorId ->
                signUpUser(lessorId) // Call signUpUser only after getting the ID
            }
        }
    }

    private fun signUpUser(lessorId: String) {
        val firstname = ""
        val lastname = ""
        val middlename = ""
        val phone = ""
        val email = findViewById<EditText>(R.id.inp_lessor_email).text.toString().trim()
        val password = findViewById<EditText>(R.id.inp_lessor_password).text.toString().trim()

        // Validate Fields
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        // Hash the password using SHA-256
        val hashedPassword = hashPassword(password)

        // Create Account in Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Prepare User Data for Firestore
                    val user = hashMapOf(
                        "email" to email,
                        "password" to hashedPassword,  // 🔐 Hashed password
                        "firstname" to firstname,
                        "lastname" to lastname,
                        "middlename" to middlename,
                        "phone" to phone,
                        "status" to false  // Default status: unverified
                    )

                    // Store User Data in Firestore under "user_student" collection
                    db.collection("user_lessor").document(lessorId)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Login_Tenants::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Firestore Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                } else {
                    Toast.makeText(this, "Sign-up failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    // Function to hash password using SHA-256
    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }  // Convert to hex string
    }

    private fun fetchAndUpdateLessorId(callback: (String) -> Unit) {
        val indexRef = db.collection("indexes").document("lessor_index")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(indexRef)
            val currentIndex = snapshot.getLong("index") ?: 0
            val newIndex = currentIndex + 1
            val formattedLessorId = "L-%05d".format(newIndex)  // Format as L-00000

            transaction.update(indexRef, "index", newIndex)  // Increment index
            return@runTransaction formattedLessorId  // ✅ Corrected return statement
        }.addOnSuccessListener { lessorId ->
            callback(lessorId)
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to fetch lessor ID: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
