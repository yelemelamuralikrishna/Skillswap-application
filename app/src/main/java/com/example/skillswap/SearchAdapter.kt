package com.example.skillswap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(private val studentList: List<Student>) :
    RecyclerView.Adapter<SearchAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtCollege: TextView = itemView.findViewById(R.id.txtCollege)
        val txtSkillHave: TextView = itemView.findViewById(R.id.txtSkillHave)
        val txtSkillWant: TextView = itemView.findViewById(R.id.txtSkillWant)
        val txtPhone: TextView = itemView.findViewById(R.id.txtPhone)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)

        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        val student = studentList[position]

        holder.txtName.text = "Name : ${student.name}"
        holder.txtCollege.text = "College : ${student.college}"
        holder.txtSkillHave.text = "Has : ${student.skillHave}"
        holder.txtSkillWant.text = "Wants : ${student.skillWant}"
        holder.txtPhone.text = "Phone : ${student.phone}"

    }
}