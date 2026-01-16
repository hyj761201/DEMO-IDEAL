package com.example.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val onEditClick: (StudentDTO) -> Unit,
    private val onDeleteClick: (StudentDTO) -> Unit
) : ListAdapter<StudentDTO, StudentAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.nameText)
        private val emailText: TextView = itemView.findViewById(R.id.emailText)
        private val editButton: Button = itemView.findViewById(R.id.editButton)
        private val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(student: StudentDTO) {
            nameText.text = student.name ?: "未知"
            emailText.text = student.email ?: "未知"
            editButton.setOnClickListener {
                onEditClick(student)
            }
            deleteButton.setOnClickListener {
                onDeleteClick(student)
            }
        }
    }

    class StudentDiffCallback : DiffUtil.ItemCallback<StudentDTO>() {
        override fun areItemsTheSame(oldItem: StudentDTO, newItem: StudentDTO): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StudentDTO, newItem: StudentDTO): Boolean {
            return oldItem == newItem
        }
    }
}
