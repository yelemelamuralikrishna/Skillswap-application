package com.example.skillswap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchAdapter(private val studentList: List<Student>) :
    RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtCollege: TextView = itemView.findViewById(R.id.txtCollege)
        val txtSkillHave: TextView = itemView.findViewById(R.id.txtSkillHave)
        val txtSkillWant: TextView = itemView.findViewById(R.id.txtSkillWant)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val student = studentList[position]

        holder.txtName.text = student.name
        holder.txtCollege.text = "College: ${student.college}"
        holder.txtSkillHave.text = "Has: ${student.skillHave}"
        holder.txtSkillWant.text = "Wants: ${student.skillWant}"

    }
}