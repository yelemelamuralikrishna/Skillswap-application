package com.example.skillswap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val name = findViewById<TextInputEditText>(R.id.etName)
        val email = findViewById<TextInputEditText>(R.id.etEmail)
        val phone = findViewById<TextInputEditText>(R.id.etPhone)
        val college = findViewById<TextInputEditText>(R.id.etCollege)
        val branch = findViewById<TextInputEditText>(R.id.etBranch)
        val year = findViewById<TextInputEditText>(R.id.etYear)
        val skillHave = findViewById<TextInputEditText>(R.id.etSkillHave)
        val skillWant = findViewById<TextInputEditText>(R.id.etSkillWant)
        val password = findViewById<TextInputEditText>(R.id.etPassword)
        val confirmPassword = findViewById<TextInputEditText>(R.id.etConfirmPassword)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val registerBtn = findViewById<Button>(R.id.btnRegister)
        val loginText = findViewById<TextView>(R.id.txtLogin)
        val realtimeDb = FirebaseDatabase.getInstance().reference

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        registerBtn.setOnClickListener {

            val userName = name.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userPhone = phone.text.toString().trim()
            val userCollege = college.text.toString().trim()
            val userBranch = branch.text.toString().trim()
            val userYear = year.text.toString().trim()
            val userSkillHave = skillHave.text.toString().trim()
            val userSkillWant = skillWant.text.toString().trim()
            val userPassword = password.text.toString().trim()
            val userConfirm = confirmPassword.text.toString().trim()

            if (userName.isEmpty() ||
                userEmail.isEmpty() ||
                userPhone.isEmpty() ||
                userCollege.isEmpty() ||
                userBranch.isEmpty() ||
                userYear.isEmpty() ||
                userSkillHave.isEmpty() ||
                userSkillWant.isEmpty() ||
                userPassword.isEmpty() ||
                userConfirm.isEmpty()) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userPassword != userConfirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            registerBtn.visibility = View.GONE

            Log.d("RegisterActivity", "Attempting to create user: $userEmail")

            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Log.d("RegisterActivity", "Auth Successful. UID: ${auth.currentUser?.uid}")
                        val uid = auth.currentUser!!.uid

                        val user = hashMapOf(
                            "name" to userName,
                            "email" to userEmail,
                            "phone" to userPhone,
                            "college" to userCollege,
                            "branch" to userBranch,
                            "year" to userYear,
                            "skillHave" to userSkillHave,
                            "skillWant" to userSkillWant
                        )

                        db.collection("students")
                            .document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                realtimeDb.child("students")
                                    .child(uid)
                                    .setValue(user)
                                Log.d("RegisterActivity", "Firestore Data Saved Successfully")
                                progressBar.visibility = View.GONE

                                Toast.makeText(
                                    this,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(
                                    Intent(
                                        this,
                                        LoginActivity::class.java
                                    )
                                )

                                finish()

                            }
                            .addOnFailureListener { e ->
                                Log.e("RegisterActivity", "Firestore Error", e)
                                progressBar.visibility = View.GONE
                                registerBtn.visibility = View.VISIBLE

                                Toast.makeText(
                                    this,
                                    "Failed to save user: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                    } else {
                        Log.e("RegisterActivity", "Auth Failed", task.exception)
                        progressBar.visibility = View.GONE
                        registerBtn.visibility = View.VISIBLE

                        val errorMsg = task.exception?.message ?: "Unknown Error"
                        Toast.makeText(
                            this,
                            "Registration Failed: $errorMsg",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }

        }

    }
}