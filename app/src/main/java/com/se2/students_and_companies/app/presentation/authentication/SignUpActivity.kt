package com.se2.students_and_companies.app.presentation.authentication

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.se2.base.Common.Constant.skillSuggestions
import com.se2.base.Common.Constant.universitySuggestions
import com.se2.students_and_companies.R
import com.se2.students_and_companies.app.data.dto.RegisterStudentDto
import com.se2.students_and_companies.app.presentation.authentication.adapter.AttributeAdapter
import com.se2.students_and_companies.app.presentation.authentication.viewmodel.AuthenticationViewModel
import com.se2.base.presentation.activity.BaseActivity
import com.se2.base.Common.Result
import com.se2.students_and_companies.databinding.ActivitySignupBinding
import com.tyme.base.Common.FragmentEnum
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class SignUpActivity : BaseActivity(R.layout.activity_signup) {
    private val viewModel: AuthenticationViewModel by viewModel()
    private lateinit var binding: ActivitySignupBinding
    private val autocompleteFragment = SuggestionFragment()
    private var currentAddress: String = ""
    private lateinit var adapter: AttributeAdapter
    private val attributes = mutableListOf<String>()
    private val PICK_FILE_REQUEST = 1
    private var fileUri: Uri? = null
    private var CVURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Push edit text programmatically
        binding = ActivitySignupBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)

        initAdapter()
        initPlaceSuggestion()

        val uniSuggestionAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, universitySuggestions)
        // Create an ArrayAdapter using the suggestions

        // Set the adapter to AutoCompleteTextView
        binding.editTextUni.setAdapter(uniSuggestionAdapter)
        binding.editTextUni.threshold = 1

        binding.CVUploadBtn.setOnClickListener {
            openFileChooser()
        }

        binding.signUpBackBtn.setOnClickListener { finish() }
        binding.addSkillBtn.setOnClickListener {
            val skillSuggestionAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, skillSuggestions)
            // Create an ArrayAdapter using the suggestions
            val editText = AutoCompleteTextView(this).apply {
                hint = "Enter Attribute"
            }

            // Set the adapter to AutoCompleteTextView
            editText.setAdapter(skillSuggestionAdapter)

            editText.threshold = 1

            // Show an AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Add New Item")
                .setView(editText) // Set the EditText as the dialog view
                .setPositiveButton("Add") { _, _ ->
                    val newItem = editText.text.toString()
                    if (newItem.isNotBlank()) {
                        addItem(newItem)
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

        binding.signUpBtn.setOnClickListener{
            uploadFile()
        }
    }

    private fun initPlaceSuggestion() {
        // Initialize the AutocompleteSupportFragment.
        Places.initialize(this, "AIzaSyDPxg4N719AUH83XbzhUnQ__orEKjvp3no", Locale.ENGLISH)
        // Add the fragment dynamically
        supportFragmentManager.beginTransaction()
            .replace(R.id.address_autocomplete_fragment, autocompleteFragment, "autocomplete_fragment")
            .commit()
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                currentAddress = place.address?.toString() ?: "Error"
            }

            override fun onError(status: Status) {

            }
        })
    }

    private fun initAdapter() {
        adapter = AttributeAdapter(attributes) { position ->
            deleteItem(position)
        }
        binding.attributeRecycleView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.attributeRecycleView.adapter = adapter
    }

    override fun onStart() {
        // Check Log in Response
            viewModel.registerResponse.observe(this) {
                    response ->
                when (response) {
                    is Result.Success -> {
                        Toast.makeText(this, "Register Sucesfully", Toast.LENGTH_LONG).show()
                        finish()
                    }
                        // On Loading - Open dialog with loading progress view
                    is Result.Loading -> {
                        binding.signUpBtn.visibility = View.GONE
                    }
                        // Error - Change dialog to error message
                    else -> {
                        Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_SHORT).show()

                    }
            }
        }
        super.onStart()
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("taitest", resultCode.toString() + "  " + requestCode.toString())
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data
            // Get content resolver
            val contentResolver = contentResolver

            // Query file details
            fileUri?.let { uri ->
                val cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        // Get file name
                        val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val fileName = if (displayNameIndex != -1) it.getString(displayNameIndex) else "Unknown"

                        // Get file size in KB
                        val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                        val fileSizeInBytes = if (sizeIndex != -1) it.getLong(sizeIndex) else 0L
                        val fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0) // Convert bytes to MB
                        val fileSizeInKB = fileSizeInBytes / 1024

                        // Show file details
                        binding.CVNameText.text = fileName
                        if (fileSizeInMB < 1) {
                            binding.CVSizeText.text = fileSizeInKB.toString() + "KB"
                        } else {
                            binding.CVSizeText.text = fileSizeInMB.toString() + "MB"
                        }
                        binding.CVView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun uploadFile() {
        val storage = Firebase.storage("gs://se2-polimi-taile.firebasestorage.app")
        val storageReference = storage.reference
        if (fileUri != null) {
            val fileRef = storageReference.child("uploads/${System.currentTimeMillis()}")

            val uploadTask = fileRef.putFile(fileUri!!)
            uploadTask.addOnSuccessListener {
                // Get the download URL
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    if(!checkEditTextEmpty()) {
                        val student = RegisterStudentDto(
                            fullName = binding.editTextName.text.toString(),
                            email = binding.editTextEmail.text.toString(),
                            password = binding.editTextPassword.text.toString(),
                            gender = "male",
                            phoneNumber = binding.editTextMobile.text.toString(),
                            aboutYou = "",
                            address = autocompleteFragment.view?.findViewById<EditText>(com.google.android.libraries.places.R.id.places_autocomplete_search_input)?.text.toString(),
                            university = binding.editTextUni.text.toString(),
                            personalEmail = "example@gmail.com",
                            attributes = attributes.joinToString(", "),
                            role = 2,
                            cv = uri.toString()
                        )
                        viewModel.register(student)
                    } else {
                        Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to Upload CV to Server", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Upload Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addItem(newItem: String) {
        attributes.add(newItem)
        adapter.notifyItemInserted(attributes.size - 1) // Notify RecyclerView of the new item
    }

    private fun deleteItem(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Accept") { _, _ ->
                if (position >= 0 && position < attributes.size) {
                    attributes.removeAt(position)
                    adapter.notifyItemRemoved(position) // Notify RecyclerView of the removal
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun isAttributeInList(attribute: String): Boolean {
        return attributes.any { it.equals(attribute, ignoreCase = true) }
    }

    private fun checkEditTextEmpty(): Boolean {
        // Get all the EditText views
        val editTexts = listOf(
            binding.editTextName,
            binding.editTextMobile,
            binding.editTextPassword,
            binding.editTextUni,
            binding.editTextEmail
        )

        // Check if any EditText is not empty
        return editTexts.all { it.text.isNullOrEmpty() }
    }
}