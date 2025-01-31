package com.se2.account_profile.presentation.ui.ui.presentation.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.rangeTo
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.storage
import com.se2.account_profile.R
import com.se2.account_profile.databinding.ActivityProfileBinding
import com.se2.account_profile.databinding.DialogFeedbackBinding
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackDto
import com.se2.account_profile.presentation.ui.ui.data.dto.FeedbackUserDto
import com.se2.account_profile.presentation.ui.ui.presentation.viewmodel.ProfileViewModel
import com.se2.base.Common.Result
import com.tyme.base.Common.FragmentEnum
import com.se2.base.ext.FragmentListener
import com.se2.base.model.dto.UserDto
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.activity_profile) {
    lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private var fragmentListener: FragmentListener? = null // Call to Navigation Activity to change screen
    private val PICK_FILE_REQUEST = 1
    private var fileUri: Uri? = null
    private var userID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileBinding.inflate(layoutInflater) //initializing the binding class
        userID = arguments?.getInt("userID")!!
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the activity implements the callback interface
        if (context is FragmentListener) {
            fragmentListener = context
        } else {
            throw IllegalArgumentException("Activity implements listener wrongly")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase in this fragment

        binding.signOutBtm.setOnClickListener{
            fragmentListener?.onNavigate(FragmentEnum.Signout)
        }

        binding.sendFeedbackBtn.setOnClickListener {
            showFeedbackDialog()
        }

        viewModel.sendFeedbackResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Success -> {
                    Toast.makeText(context, "Send feedback Successfully", Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                }

                else -> {
                    Toast.makeText(context, "Error in sending feedback", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showFeedbackDialog() {
        // Use View Binding for the dialog layout
        val dialogBinding = DialogFeedbackBinding.inflate(LayoutInflater.from(requireContext()))

        // Create and show the dialog
        MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .setPositiveButton("Submit") { _, _ ->
                val title = dialogBinding.etTitle.text.toString().trim()
                val description = dialogBinding.etDescription.text.toString().trim()
                val rating = dialogBinding.ratingBar.rating.toInt()

                // Validate inputs
                if (title.isEmpty() || description.isEmpty() || rating == 0) {
                    Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.sendFeedback(
                        FeedbackDto(
                            title = title,
                            description = description,
                            rating = rating,
                            user = FeedbackUserDto(id = userID)
                        )
                    )
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

}
