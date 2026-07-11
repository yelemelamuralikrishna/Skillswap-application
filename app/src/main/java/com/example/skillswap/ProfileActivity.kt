package com.example.skillswap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var college: TextView
    lateinit var branch: TextView
    lateinit var year: TextView
    lateinit var phone: TextView
    lateinit var skillHave: TextView
    lateinit var skillWant: TextView
    lateinit var editBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        name = findViewById(R.id.txtName)
        email = findViewById(R.id.txtEmail)
        college = findViewById(R.id.txtCollege)
        branch = findViewById(R.id.txtBranch)
        year = findViewById(R.id.txtYear)
        phone = findViewById(R.id.txtPhone)
        skillHave = findViewById(R.id.txtSkillHave)
        skillWant = findViewById(R.id.txtSkillWant)

        editBtn = findViewById(R.id.editBtn)

        val uid = auth.currentUser?.uid

        if (uid != null) {

            db.collection("students")
                .document(uid)
                .get()
                .addOnSuccessListener {

                    name.text = it.getString("name")
                    email.text = it.getString("email")
                    college.text = it.getString("college")
                    branch.text = it.getString("branch")
                    year.text = it.getString("year")
                    phone.text = it.getString("phone")
                    skillHave.text = it.getString("skillHave")
                    skillWant.text = it.getString("skillWant")

                }

        }

        editBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    EditProfileActivity::class.java
                )
            )

        }

    }

}