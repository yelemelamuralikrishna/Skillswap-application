package com.example.skillswap

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etName: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etCollege: TextInputEditText
    private lateinit var etBranch: TextInputEditText
    private lateinit var etYear: TextInputEditText
    private lateinit var etSkillHave: TextInputEditText
    private lateinit var etSkillWant: TextInputEditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etName = findViewById(R.id.etName)
        etPhone = findViewById(R.id.etPhone)
        etCollege = findViewById(R.id.etCollege)
        etBranch = findViewById(R.id.etBranch)
        etYear = findViewById(R.id.etYear)
        etSkillHave = findViewById(R.id.etSkillHave)
        etSkillWant = findViewById(R.id.etSkillWant)
        btnSave = findViewById(R.id.btnSave)

        loadProfile()

        btnSave.setOnClickListener {
            updateProfile()
        }
    }

    private fun loadProfile() {

        val uid = auth.currentUser?.uid ?: return

        db.collection("students")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    etName.setText(document.getString("name"))
                    etPhone.setText(document.getString("phone"))
                    etCollege.setText(document.getString("college"))
                    etBranch.setText(document.getString("branch"))
                    etYear.setText(document.getString("year"))
                    etSkillHave.setText(document.getString("skillHave"))
                    etSkillWant.setText(document.getString("skillWant"))

                }

            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to load profile",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

    private fun updateProfile() {

        val uid = auth.currentUser?.uid ?: return

        val user = hashMapOf(

            "name" to etName.text.toString().trim(),
            "phone" to etPhone.text.toString().trim(),
            "college" to etCollege.text.toString().trim(),
            "branch" to etBranch.text.toString().trim(),
            "year" to etYear.text.toString().trim(),
            "skillHave" to etSkillHave.text.toString().trim(),
            "skillWant" to etSkillWant.text.toString().trim()

        )

        db.collection("students")
            .document(uid)
            .update(user as Map<String, Any>)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Profile Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Update Failed",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

}