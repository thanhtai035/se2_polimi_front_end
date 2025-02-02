package com.se2.student_dashboard.presentation.ui

import Internship
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.se2.base.Common.Result
import com.se2.base.ext.FragmentListener
import com.se2.base.model.Application
import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.data.dto.InternshipRefDto
import com.se2.internship_detail.data.dto.StudentRefDto
import com.se2.internship_detail.databinding.InternshipDetailActivityBinding
import com.se2.internship_detail.presentation.ui.AboutFragment
import com.se2.internship_detail.presentation.ui.CompanyFragment
import com.se2.internship_detail.presentation.ui.ModalFragment
import com.se2.internship_detail.presentation.viewmodel.InternshipDetailViewModel
import com.se2.student_dashboard.R
import com.se2.student_dashboard.data.dto.InterviewFormDto
import com.se2.student_dashboard.data.dto.QuestionnaireFormDto
import com.se2.student_dashboard.data.dto.ShareApplicationDto
import com.se2.student_dashboard.data.dto.StatusUpdateDto
import com.se2.student_dashboard.databinding.ApplicationDetailActivityBinding
import com.se2.student_dashboard.presentation.adapter.ApplicationAdapter
import com.se2.student_dashboard.presentation.viewmodel.ApplicationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApplicationDetailActivity : AppCompatActivity() {
    lateinit var binding: ApplicationDetailActivityBinding
    private val viewModel: ApplicationViewModel by viewModel()
    private lateinit var items: ArrayList<Application>
    private var studentID: Int? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ApplicationDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBundle()
        binding.applicationRecycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ApplicationAdapter(items) {
            item, isInterview ->
            addQuestionnaireInterview(item, isInterview)
        }
        binding.applicationRecycleView.adapter = adapter
        binding.applicationDetailBackBtn.setOnClickListener {
            finish()
        }
    }

    fun addQuestionnaireInterview(application: Application, btnClicked: Int) {
        if(btnClicked < 2) {
            val builder = AlertDialog.Builder(this)
            val inflater = LayoutInflater.from(this)
            val dialogView = inflater.inflate(R.layout.dialog_interview_questionnaire, null)

            val editText1 = dialogView.findViewById<EditText>(R.id.editText1)
            val editText2 = dialogView.findViewById<EditText>(R.id.editText2)
            val editText3 = dialogView.findViewById<EditText>(R.id.editText3)

            var title = "Add Interview"
            if (btnClicked == 0) {
                title = "Add Questionnaire"
            }
            builder.setView(dialogView)
                .setTitle(title)
                .setPositiveButton("Submit") { _, _ ->
                    val titleTxt = editText1.text.toString()
                    val input2 = editText2.text.toString()
                    val url = input2.trim()
                    val description = editText3.text.toString()

                    if (titleTxt.isNotBlank() && input2.isNotBlank() && Patterns.WEB_URL.matcher(url)
                            .matches()
                    ) {
                        if (btnClicked == 0) {
                            val formDto = InterviewFormDto(
                                ShareApplicationDto(
                                    application.id ?: ""
                                ),
                                title = titleTxt,
                                link = url,
                                location = description,
                                passed = false
                            )
                            viewModel.postInterview(formDto)
                        } else if (btnClicked == 1) {
                            val formDto = QuestionnaireFormDto(
                                ShareApplicationDto(
                                    application.id ?: ""
                                ),
                                link = url,
                                name = titleTxt,
                                passsed = false
                            )
                            viewModel.postQuestionnaire(formDto)
                        }
                    } else {
                        Toast.makeText(this, "Empty input or invalid URL", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }

            val dialog = builder.create()
            dialog.show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Set Status to the application")
                .setMessage("Accept or reject the application")
                .setPositiveButton("Accept") { _, _ ->
                    viewModel.updateStatus(application.id?:"", StatusUpdateDto("Qualified"))
                }
                .setNegativeButton("Reject") { _, _ ->
                    viewModel.updateStatus(application.id?:"", StatusUpdateDto("Rejected"))
                }
                .show()
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getBundle() {
        studentID = intent.getIntExtra("userID", 0) as Int
        items = intent.getSerializableExtra("list") as ArrayList<Application>
    }


    override fun onStart() {
        // Check Log in Response
        viewModel.postInterviewResponse.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    if (response.data!!) {
                        val resultIntent = Intent()
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                        Toast.makeText(this, "Add Interview Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }  else {
                        Toast.makeText(this, "Error in adding interview", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Result.Loading -> {
                }
                // Error - Change dialog to error message
                else -> {
                    Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.postQuestionnaireResponse.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    if (response.data!!) {
                        val resultIntent = Intent()
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                        Toast.makeText(this, "Add Questionnaire Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }  else {
                        Toast.makeText(this, "Error in adding Questionnaire", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Result.Loading -> {
                }
                // Error - Change dialog to error message
                else -> {
                    Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.updateStatusResponse.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    finish()
                    Toast.makeText(this, "Set action to status successfully", Toast.LENGTH_SHORT)
                        .show()
                }
                is Result.Loading -> {
                }
                // Error - Change dialog to error message
                else -> {
                    Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onStart()
    }
}