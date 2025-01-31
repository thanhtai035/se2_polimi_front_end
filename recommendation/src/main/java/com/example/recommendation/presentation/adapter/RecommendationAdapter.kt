package com.example.recommendation.presentation.adapter

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
import com.example.recommendation.databinding.RecommendationViewholderBinding
import com.se2.base.model.Application
import com.se2.internship_detail.presentation.ui.InternshipDetailActivity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class RecommendationAdapter(private val items: List<Internship>, private val userID: Int, private val applicationList: ArrayList<Application>) :
    RecyclerView.Adapter<RecommendationAdapter.Viewholder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationAdapter.Viewholder {
        context = parent.context
        val binding =
             RecommendationViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        val requiredSkills = splitFormattedString(item.requiredSkills)
        var applied = false

        holder.binding.titleTxt.text = item.title
        holder.binding.companyTxt.text = item.company.fullName

        holder.binding.locationTxt.text = item.company.address
        holder.binding.salaryTxt.text = item.salary
        if (requiredSkills.get(0) != null) {
            holder.binding.Skill1.text = requiredSkills.get(0)
        } else holder.binding.Skill1.visibility = View.GONE

        if (requiredSkills.get(1) != null) {
            holder.binding.Skill2.text = requiredSkills.get(1)
        } else holder.binding.Skill2.visibility = View.GONE

        if (requiredSkills.get(2) != null) {
            holder.binding.Skill3.text = requiredSkills.get(2)
        } else holder.binding.Skill3.visibility = View.GONE

        if (isIdInApplications(applicationList, item.id)) {
            holder.binding.appliedText.visibility = View.VISIBLE
            applied = true
        } else {
            holder.binding.appliedText.visibility = View.INVISIBLE
        }

        holder.binding.createdTxt.text = formatDateTime(item.createdAt)
        holder.binding.scoreTxt.text = "Match Score: " + item.matchPercentage.toString()

        Glide.with(holder.itemView.context)
            .load(item.company.image)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, InternshipDetailActivity::class.java)
            Log.d("taitest", userID.toString())
            intent.putExtra("object", item)
            intent.putExtra("userID", userID)
            intent.putExtra("userRole", 0)
            intent.putExtra("applied", applied)

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: RecommendationViewholderBinding) :
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