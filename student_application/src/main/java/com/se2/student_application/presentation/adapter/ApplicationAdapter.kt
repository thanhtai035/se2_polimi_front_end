package com.se2.student_application.presentation.adapter

import Internship
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.se2.base.model.Application
import com.se2.student_application.R
import com.se2.student_application.databinding.ApplicationViewholderBinding
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class ApplicationAdapter(private val items: ArrayList<Application>, private val onClick: (Application) -> Unit) :
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

        if (item.questionnaires.size != 0 && !item.status.equals("Qualified")) {
            holder.binding.seeQuestionnaioreBtn.setOnClickListener {
                showCustomDialog(context, item, false)
            }
        } else {holder.binding.seeQuestionnaioreBtn.visibility = View.GONE}

        if (item.interviews.size != 0 && !item.status.equals("Qualified")) {
            holder.binding.seeInterviewBtn.setOnClickListener {
                showCustomDialog(context, item, false)
            }
        } else {holder.binding.seeInterviewBtn.visibility = View.GONE}

        if(item.status.equals("Qualified") && isAlreadyInProgressApplication(items)) {
            holder.binding.answerBtn.visibility = View.VISIBLE
        }
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
        } else if (item.status.equals("Rejected")) {
            holder.binding.statusTxt.text = item.status
            holder.binding.statusTxt.background = ContextCompat.getDrawable(context, com.se2.base.R.drawable.red_button_bg)
        } else if (item.status.equals("In Progress")) {
            holder.binding.statusTxt.text = item.status
            holder.binding.statusTxt.background = ContextCompat.getDrawable(context, com.se2.base.R.drawable.green_button_bg)
        } else {
            holder.binding.statusTxt.text = item.status
        }
        holder.binding.answerBtn.setOnClickListener {
            if (item.status.equals("Qualified") && !isAlreadyInProgressApplication(items)) {
                Toast.makeText(context, "There is already an internship In Progress", Toast.LENGTH_SHORT).show()
            } else if(item.status.equals("Qualified") && !isAlreadyInProgressApplication(items)) {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Viewholder(val binding: ApplicationViewholderBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun splitFormattedString(input: String?): List<String> {
        return input?.split(", ")?.map { it.trim() } ?: emptyList()
    }

    fun isAlreadyInProgressApplication(applications: ArrayList<Application>): Boolean {
        return applications.any { it.status.equals("In Progress")  }
    }

    fun formatDateTime(dateTime: String?): String {
        // Parse the input date-time string
        val parsedDateTime = ZonedDateTime.parse(dateTime)

        // Define the desired output format
        val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy, h:mm a")

        // Format and return
        return parsedDateTime.format(formatter)
    }

    fun showCustomDialog(context: Context, application: Application, isInterview: Boolean) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)

        val title = dialogView.findViewById<TextView>(R.id.titleTxt)
        val description = dialogView.findViewById<TextView>(R.id.descriptionTxt)
        val linkText = dialogView.findViewById<TextView>(R.id.linkText)

        if(isInterview) {
            title.text = application.interviews.get(0).title
            description.text = application.interviews.get(0).description
            linkText.text = application.interviews.get(0).link
        } else {
            title.text = application.questionnaires.get(0).name
            description.text = application.questionnaires.get(0).description
            linkText.text = application.questionnaires.get(0).link
        }

        // Make the link clickable
        linkText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkText.text.toString()))
            context.startActivity(intent)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

}