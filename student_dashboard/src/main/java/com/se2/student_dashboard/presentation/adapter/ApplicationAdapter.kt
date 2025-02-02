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
import com.se2.internship_detail.presentation.ui.InternshipDetailActivity
import com.se2.student_dashboard.databinding.ViewholderInternshipBinding
import com.se2.student_dashboard.databinding.ViewholderStudentApplicationBinding
import com.se2.student_dashboard.presentation.ui.ApplicationDetailActivity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ApplicationAdapter(private val items: ArrayList<Application>,  private val onClick: (Application, Int) -> Unit) :
    RecyclerView.Adapter<ApplicationAdapter.Viewholder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationAdapter.Viewholder {
        context = parent.context
        val binding =
            ViewholderStudentApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        var requiredSkills: List<String>
        try {
            requiredSkills = splitFormattedString(item.student?.attributes)
        } catch (e: Exception) {
            requiredSkills = arrayListOf(item.student?.attributes?:"")
        }
        val adapter = StudentSkillAdapter(requiredSkills)
        holder.binding.skillRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        holder.binding.skillRecycleView.adapter = adapter
        holder.binding.nameTxt.text = item.student?.fullName
        holder.binding.createdTxt.text = formatDateTime(item.createdAt)
        holder.binding.locationTxt.text = item.student?.address
        holder.binding.uniTxt.text = item.student?.university

        if (item.status.equals("Qualified")) {
            holder.binding.agreeTxt.visibility = View.VISIBLE
            holder.binding.decisionView.visibility = View.GONE
        }
        else if (item.status.equals("Rejected")) {
            holder.binding.rejectedTxt.visibility = View.VISIBLE
            holder.binding.decisionView.visibility = View.GONE
        }
        if(item.questionnaires.size > 0) {
            holder.binding.addQuestionnaireBtn.visibility = View.GONE
            holder.binding.addQuestionnaireTxt.visibility = View.VISIBLE
        }
        if(item.interviews.size > 0) {
            holder.binding.addInterviewBtn.visibility = View.GONE
            holder.binding.addInterviewTxt.visibility = View.VISIBLE
        }
        holder.binding.addInterviewBtn.setOnClickListener {
            onClick(item, 0)
        }
        holder.binding.addQuestionnaireBtn.setOnClickListener {
            onClick(item, 1)
        }
        holder.binding.answerBtn.setOnClickListener {
            onClick(item, 2)
        }
        holder.binding.viewCVBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.student?.cv_link?: "https://pdfobject.com/pdf/sample.pdf"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Ensure it works outside activities
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "No application found to open PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: ViewholderStudentApplicationBinding) :
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
}