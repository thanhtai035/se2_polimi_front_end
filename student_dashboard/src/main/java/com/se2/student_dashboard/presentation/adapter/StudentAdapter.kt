package com.se2.student_dashboard.presentation.adapter

import Internship
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.se2.base.model.Application
import com.se2.base.model.User
import com.se2.internship_detail.presentation.ui.InternshipDetailActivity
import com.se2.student_dashboard.databinding.ViewholderInternshipBinding
import com.se2.student_dashboard.databinding.ViewholderStudentApplicationBinding
import com.se2.student_dashboard.databinding.ViewholderStudentProfiBinding
import com.se2.student_dashboard.presentation.ui.ApplicationDetailActivity
import okhttp3.internal.ignoreIoExceptions
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class StudentAdapter(private val items: List<User>,
                     private val applications: ArrayList<Application>) :
    RecyclerView.Adapter<StudentAdapter.Viewholder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.Viewholder {
        context = parent.context
        val binding =
            ViewholderStudentProfiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        var requiredSkills: List<String>
        try {
            requiredSkills = splitFormattedString(item.attributes)
        } catch (e: Exception) {
            requiredSkills = arrayListOf(item.attributes?:"")
        }
        val adapter = StudentSkillAdapter(requiredSkills)
        holder.binding.uniSkillRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        holder.binding.uniSkillRecycleView.adapter = adapter
        holder.binding.uniStudentNameTxt.text = item.fullName
        holder.binding.uniLocationTxt.text = item.address
        holder.binding.viewCVUniBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.cv_link?: "https://pdfobject.com/pdf/sample.pdf"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Ensure it works outside activities
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_SHORT).show()
            }
        }
        if(findApplicationById(applications ,item.id).size > 0) {
            holder.binding.uniInprogressStudent.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: ViewholderStudentProfiBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun formatDateTime(dateTime: String?): String {
        // Parse the input date-time string
        val parsedDateTime = ZonedDateTime.parse(dateTime)

        // Define the desired output format
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, h:mm a")

        // Format and return
        return parsedDateTime.format(formatter)
    }

    fun splitFormattedString(input: String?): List<String> {
        return input?.split(", ")?.map { it.trim() } ?: emptyList()
    }

    fun findApplicationById(applications: ArrayList<Application>, id: Int): List<Application> {
        return applications.filter { it.student?.id == id && it.status.equals("In Progress")}
    }
}