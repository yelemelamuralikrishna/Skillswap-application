package com.example.skillswap

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddSkillActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skill)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val skillHave = findViewById<TextInputEditText>(R.id.etSkillHave)
        val skillWant = findViewById<TextInputEditText>(R.id.etSkillWant)
        val saveBtn = findViewById<Button>(R.id.btnSaveSkill)

        saveBtn.setOnClickListener {

            val uid = auth.currentUser?.uid

            if (uid == null) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updates = hashMapOf<String, Any>(
                "skillHave" to skillHave.text.toString().trim(),
                "skillWant" to skillWant.text.toString().trim()
            )

            db.collection("students")
                .document(uid)
                .update(updates)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Skills Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Failed to Update Skills",
                        Toast.LENGTH_SHORT
                    ).show()

                }

        }

    }
}