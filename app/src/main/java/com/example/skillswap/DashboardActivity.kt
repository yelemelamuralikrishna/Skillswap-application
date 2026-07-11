package com.example.skillswap

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val mainView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0) // Header covers top
            insets
        }

        val tvUserName = findViewById<TextView>(R.id.userName)
        val tvSkillHave = findViewById<TextView>(R.id.tvSkillHave)
        val tvSkillWant = findViewById<TextView>(R.id.tvSkillWant)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Load user data
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("students").document(currentUser.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name") ?: "User"
                        val have = document.getString("skillHave") ?: "None"
                        val want = document.getString("skillWant") ?: "None"

                        tvUserName.text = name
                        tvSkillHave.text = "Have: $have"
                        tvSkillWant.text = "Want to Learn: $want"
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
                }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    false
                }
                R.id.nav_add -> {
                    startActivity(Intent(this, AddSkillActivity::class.java))
                    false
                }
                R.id.nav_match -> {
                    startActivity(Intent(this, MatchActivity::class.java))
                    false
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    false
                }
                else -> false
            }
        }
    }
}