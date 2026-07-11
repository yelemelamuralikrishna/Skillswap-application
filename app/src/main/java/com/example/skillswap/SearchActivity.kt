package com.example.skillswap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter

    private val studentList = ArrayList<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchBox = findViewById<EditText>(R.id.etSearch)
        val btnSearch = findViewById<Button>(R.id.btnSearch)

        recyclerView = findViewById(R.id.recyclerSearch)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SearchAdapter(studentList)
        recyclerView.adapter = adapter

        // Show all students initially
        loadStudents("")

        btnSearch.setOnClickListener {
            val text = searchBox.text.toString().trim().lowercase()
            loadStudents(text)
        }
    }

    private fun loadStudents(searchText: String) {

        FirebaseFirestore.getInstance()
            .collection("students")
            .get()
            .addOnSuccessListener { result ->

                studentList.clear()

                for (document in result) {

                    val student = document.toObject(Student::class.java)

                    if (
                        searchText.isEmpty() ||
                        student.name.lowercase().contains(searchText) ||
                        student.skillHave.lowercase().contains(searchText) ||
                        student.skillWant.lowercase().contains(searchText)
                    ) {
                        studentList.add(student)
                    }
                }

                adapter.notifyDataSetChanged()
            }
    }
}