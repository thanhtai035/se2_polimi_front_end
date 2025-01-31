package com.se2.student_dashboard.presentation.ui

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.se2.base.Common.Constant.skillSuggestions
import com.se2.base.Common.Result

import com.se2.base.presentation.activity.BaseActivity
import com.se2.student_dashboard.R
import com.se2.student_dashboard.data.dto.CompanyDto
import com.se2.student_dashboard.data.dto.CompanyPostInternshipDto
import com.se2.student_dashboard.data.dto.InternshipFormDto
import com.se2.student_dashboard.data.dto.PostInternshipResponseDto
import com.se2.student_dashboard.databinding.AddInternshipActivityBinding
import com.se2.student_dashboard.presentation.adapter.SkillAdapter
import com.se2.student_dashboard.presentation.viewmodel.AddInternshipViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddInternshipActivity : BaseActivity(R.layout.add_internship_activity) {
    private val viewModel: AddInternshipViewModel by inject()
    private lateinit var binding: AddInternshipActivityBinding
    private lateinit var requiredSkillAdapter: SkillAdapter
    private lateinit var optionalSkillAdapter: SkillAdapter
    private val requiredSkillList = mutableListOf<String>()
    private val optionalSkillList = mutableListOf<String>()
    private var userID = 0
    private var startDatePicked = false
    private var endDatePicked = false
    private var startCalendar = Calendar.getInstance()  // Calendar for the start date
    private var endCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Push edit text programmatically
        binding = AddInternshipActivityBinding.inflate(layoutInflater) //initializing the binding class
        userID =  intent.getIntExtra("userID", 0)
        setContentView(binding.root)

        initAdapter()

        binding.AddInternshipBackBtn.setOnClickListener { finish() }
        binding.requiredSkillAddInternshipBtn.setOnClickListener {
            initButton(true)
        }
        binding.optionalSkillAddInternshipBtn.setOnClickListener {
            initButton(false)
        }

        binding.startDateInternshipText.setOnClickListener {
            showDatePicker(true)
        }
        binding.endDateInternshipText.setOnClickListener {
            showDatePicker(false)
        }
        binding.addInternshipBtn.setOnClickListener{
            if(!checkEditTextEmpty() && startDatePicked && endDatePicked) {
                val internship = InternshipFormDto(
                    company = CompanyDto(id = userID),
                    optionalSkills = optionalSkillList.joinToString(", "),
                    requiredSkills = requiredSkillList.joinToString(", "),
                    endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        endCalendar.time
                    ),
                    startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        startCalendar.time
                    ),
                    title = binding.internshipTitleText.text.toString(),
                    salary = binding.salaryInternshipTxt.text.toString(),
                    type = "Full-time",
                    description = binding.descriptionInternshipTxt.text.toString(),
                    duration = binding.durationInternshipText.text.toString()
                )
                viewModel.postInternship(internship)
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initAdapter() {
        requiredSkillAdapter = SkillAdapter(requiredSkillList) { position ->
            deleteItem(position, true)
        }
        optionalSkillAdapter = SkillAdapter(optionalSkillList) { position ->
            deleteItem(position, false)
        }
        binding.requiredSkillRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.optionalSkillRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        binding.requiredSkillRecycleView.adapter = requiredSkillAdapter
        binding.optionalSkillRecycleView.adapter = optionalSkillAdapter
    }

    private fun initButton(requiredSkill: Boolean) {
        val skillSuggestionAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, skillSuggestions)
        // Create an ArrayAdapter using the suggestions
        val editText = AutoCompleteTextView(this).apply {
            hint = "Enter Skill"
        }

        // Set the adapter to AutoCompleteTextView
        editText.setAdapter(skillSuggestionAdapter)
        editText.threshold = 1

        // Show an AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Add New Skill")
            .setView(editText) // Set the EditText as the dialog view
            .setPositiveButton("Add") { _, _ ->
                val newItem = editText.text.toString()
                if (newItem.isNotBlank()) {
                    addItem(newItem, requiredSkill)
                } else if (isAttributeInList(newItem)) {
                    Toast.makeText(this, "Item is already added", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onStart() {
        // Check Log in Response
        super.onStart()
        viewModel.postInternshipResponse.observe(this) {
                response ->
            when(response) {
                is Result.Success -> {
                    if (response.data!!) {
                        finish()
                        Toast.makeText(this, "Send Application Successfully", Toast.LENGTH_LONG)
                            .show()
                    }  else {
                        Toast.makeText(this, "Error in sending the application", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Result.Loading -> {
                    binding.addInternshipBtn.visibility = View.GONE
                } else -> {
                }
            }
        }
    }

    private fun addItem(newItem: String, requiredSkill: Boolean) {
        if (requiredSkill) {
            requiredSkillList.add(newItem)
            requiredSkillAdapter.notifyItemInserted(requiredSkillList.size - 1) // Notify RecyclerView of the new item
        } else {
            optionalSkillList.add(newItem)
            optionalSkillAdapter.notifyItemInserted(optionalSkillList.size - 1) // Notify RecyclerView of the new item
        }
    }

    private fun deleteItem(position: Int, requiredSkill: Boolean) {
        AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Accept") { _, _ ->
                if (position >= 0 && position < requiredSkillList.size && requiredSkill) {
                    requiredSkillList.removeAt(position)
                    requiredSkillAdapter.notifyItemRemoved(position) // Notify RecyclerView of the removal
                } else if (position >= 0 && position < optionalSkillList.size && !requiredSkill) {
                    optionalSkillList.removeAt(position)
                    optionalSkillAdapter.notifyItemRemoved(position) // Notify RecyclerView of the removal
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDatePicker(isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val todayInMillis = calendar.timeInMillis

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                val selectedDateInMillis = selectedCalendar.timeInMillis

                if (isStartDate) {
                    // Start Date logic
                    if (selectedDateInMillis < todayInMillis) {
                        Toast.makeText(this, "Start date must be after today", Toast.LENGTH_SHORT).show()
                    } else {
                        startDatePicked = true
                        startCalendar = selectedCalendar
                        binding.startDateInternshipText.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCalendar.time)
                        // Update the minimum date for the end date picker to be after the start date
                        updateEndDatePickerMinDate()
                    }
                } else {
                    // End Date logic
                    if (selectedDateInMillis < todayInMillis || selectedDateInMillis <= startCalendar.timeInMillis) {
                        Toast.makeText(this, "End date must be after start date and today", Toast.LENGTH_SHORT).show()
                    } else {
                        endDatePicked = true
                        endCalendar = selectedCalendar
                        binding.endDateInternshipText.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedCalendar.time)
                    }
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the minimum date for the start or end date
        if (isStartDate) {
            datePickerDialog.datePicker.minDate = todayInMillis
        } else {
            datePickerDialog.datePicker.minDate = startCalendar.timeInMillis // Use selected start date as min for end date
        }

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    // Function to update the minimum date for the end date picker
    private fun updateEndDatePickerMinDate() {
        val datePickerDialog = DatePickerDialog(this, null, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = startCalendar.timeInMillis
    }

    private fun isAttributeInList(attribute: String): Boolean {
        return (requiredSkillList.any {
            it.equals(attribute, ignoreCase = true)} ||
                    optionalSkillList.any {
                        it.equals(attribute, ignoreCase = true)
                    }
            )}

    private fun checkEditTextEmpty(): Boolean {
        // Get all the EditText views
        val editTexts = listOf(
            binding.internshipTitleText,
            binding.durationInternshipText,
            binding.descriptionInternshipTxt,
            binding.salaryInternshipTxt,
        )
        // Check if any EditText is not empty
        return editTexts.all { it.text.isNullOrEmpty() }
    }
}


