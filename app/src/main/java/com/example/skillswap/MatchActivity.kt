package com.example.skillswap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MatchAdapter
    private val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        recyclerView = findViewById(R.id.recyclerMatches)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MatchAdapter(studentList)
        recyclerView.adapter = adapter

        loadUsers()
    }

    private fun loadUsers() {

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid

        FirebaseFirestore.getInstance()
            .collection("students")
            .get()
            .addOnSuccessListener { result ->

                studentList.clear()

                for (document in result) {

                    if (document.id != currentUser) {

                        val student = document.toObject(Student::class.java)
                        studentList.add(student)

                    }
                }

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Failed to load users",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }
}