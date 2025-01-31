package com.se2.student_dashboard.presentation.adapter

import Internship
import android.content.Context
import android.content.Intent
import android.text.SpannedString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.se2.base.model.Application
import com.se2.internship_detail.presentation.ui.InternshipDetailActivity
import com.se2.student_dashboard.R
import com.se2.student_dashboard.databinding.ViewholderInternshipBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class InternshipAdapter(private val items: List<Internship>, private val userID: Int,private val userRole: Int, private val applicationList: ArrayList<Application>) :
    RecyclerView.Adapter<InternshipAdapter.Viewholder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternshipAdapter.Viewholder {
        context = parent.context
        val binding =
            ViewholderInternshipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        val size = findApplicationById(applicationList, item.id?:"").size
        var requiredSkills: List<String>
        try {
            requiredSkills = splitFormattedString(item.requiredSkills)
        } catch (e: Exception) {
            requiredSkills = arrayListOf(item.requiredSkills?:"")
        }
        var applied = false

        holder.binding.titleTxt.text = item.title
        holder.binding.companyTxt.text = item.company.fullName
        holder.binding.locationTxt.text = item.company.address
        holder.binding.salaryTxt.text = item.salary
        if (!requiredSkills.get(0).equals("")) {
            holder.binding.Skill1.text = requiredSkills.get(0)
        } else holder.binding.Skill1.visibility = View.GONE

        if (requiredSkills.size>=2) {
            holder.binding.Skill2.text = requiredSkills.get(1)
        } else holder.binding.Skill2.visibility = View.GONE

        if (requiredSkills.size>2) {
            holder.binding.Skill3.text = requiredSkills.get(2)
        } else holder.binding.Skill3.visibility = View.GONE

        if (isIdInApplications(applicationList, item.id) && userRole == 2) {
            holder.binding.appliedText.visibility = View.VISIBLE
            applied = true
        } else if (userRole == 3 && size > 0) {
            holder.binding.appliedText.text = size.toString() + " Applications"
            holder.binding.appliedText.visibility = View.VISIBLE
        }
        else {
            holder.binding.appliedText.visibility = View.INVISIBLE
        }

        holder.binding.createdTxt.text = formatDateTime(item.createdAt)

        Glide.with(holder.itemView.context)
            .load(item.company.image)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, InternshipDetailActivity::class.java)
            Log.d("taitest", userID.toString())
            intent.putExtra("object", item)
            intent.putExtra("userID", userID)
            intent.putExtra("userRole", userRole)
            intent.putExtra("applied", applied)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: ViewholderInternshipBinding) :
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

    // Function to find an application by its id
    fun findApplicationById(applications: List<Application>, id: String): List<Application> {
        return applications.filter { it.internship?.id == id }
    }
}