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

class SignUpForStudents : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up_for_students)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth & Firestore
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageButton>(R.id.backbuttonStudents)
        backButton.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

        val signInText = findViewById<TextView>(R.id.SignInForStudents)
        signInText.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

        val signUpButton = findViewById<Button>(R.id.loginbuttonstudents)
        signUpButton.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val student_id = findViewById<EditText>(R.id.StudentID).text.toString().trim()
        val firstname = ""
        val lastname = ""
        val middlename = ""
        val phone = ""
        val email = findViewById<EditText>(R.id.StudentsSignUpEmail).text.toString().trim()
        val password = findViewById<EditText>(R.id.StudentsSignUpPassword).text.toString().trim()

        // Validate Fields
        if (student_id.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Student ID, Email, and Password are required", Toast.LENGTH_SHORT).show()
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
                        "student_id" to student_id,
                        "firstname" to firstname,
                        "lastname" to lastname,
                        "middlename" to middlename,
                        "phone" to phone,
                        "email" to email,
                        "password" to hashedPassword,  // 🔐 Hashed password
                        "status" to false  // Default status: unverified
                    )

                    // Store User Data in Firestore under "user_student" collection
                    db.collection("user_student").document(student_id)
                        .set(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginPage::class.java))
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
}