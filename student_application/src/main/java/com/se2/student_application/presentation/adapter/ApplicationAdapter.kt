package com.se2.student_application.presentation.adapter

import Internship
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.se2.base.model.Application
import com.se2.student_application.R
import com.se2.student_application.databinding.ApplicationViewholderBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ApplicationAdapter(private val items: ArrayList<Application>) :
    RecyclerView.Adapter<ApplicationAdapter.Viewholder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationAdapter.Viewholder {
        context = parent.context
        val binding =
             ApplicationViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        val requiredSkills = splitFormattedString(item.internship?.requiredSkills)

        holder.binding.titleTxt.text = item.internship?.title
        holder.binding.companyTxt.text = item.internship?.company?.fullName
        holder.binding.createdTxt.text = formatDateTime(item.createdAt)
        holder.binding.locationTxt.text = item.internship?.company?.address
        holder.binding.salaryTxt.text = item.internship?.salary
        if (requiredSkills.get(0) != null) {
            holder.binding.Skill1.text = requiredSkills.get(0)
        } else holder.binding.Skill1.visibility = View.GONE

        if (requiredSkills.get(1) != null) {
            holder.binding.Skill2.text = requiredSkills.get(1)
        } else holder.binding.Skill2.visibility = View.GONE

        if (requiredSkills.get(2) != null) {
            holder.binding.Skill3.text = requiredSkills.get(2)
        } else holder.binding.Skill3.visibility = View.GONE

        if (item.status.equals("Applied")) {
            holder.binding.statusTxt.text = "In Pending"
            holder.binding.statusTxt.background = ContextCompat.getDrawable(context, com.se2.base.R.drawable.orange_button_bg)
        } else if (item.status.equals("Cancelled")) {
            holder.binding.statusTxt.text = item.status
            holder.binding.statusTxt.background = ContextCompat.getDrawable(context, com.se2.base.R.drawable.red_button_bg)
        } else if (item.status.equals("In Progress")) {
            holder.binding.statusTxt.text = item.status
            holder.binding.statusTxt.background = ContextCompat.getDrawable(context, com.se2.base.R.drawable.green_button_bg)
        } else {
            holder.binding.statusTxt.text = item.status
        }
        holder.itemView.setOnClickListener {
            if (item.status.equals("Qualified")) {

            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: ApplicationViewholderBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun splitFormattedString(input: String?): List<String> {
        return input?.split(", ")?.map { it.trim() } ?: emptyList()
    }

    fun isIdInApplications(applications: ArrayList<Application>, targetId: String?): Boolean {
        return applications.any { it.internship?.id == targetId }
    }

    fun formatDateTime(dateTime: String?): String {
        // Parse the input date-time string
        val parsedDateTime = ZonedDateTime.parse(dateTime)

        // Define the desired output format
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, h:mm a")

        // Format and return
        return parsedDateTime.format(formatter)
    }

}