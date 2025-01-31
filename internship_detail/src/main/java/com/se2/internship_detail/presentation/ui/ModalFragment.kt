package com.se2.internship_detail.presentation.ui

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.se2.internship_detail.R
import com.se2.internship_detail.databinding.ApplyModalBinding

class ModalFragment : DialogFragment(R.layout.apply_modal) {
    private lateinit var binding: ApplyModalBinding

    interface DialogListener {
        fun onDialogResult(result: String)
    }

    private var listener: DialogListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ApplyModalBinding.inflate(layoutInflater) //initializing the binding class
        // Set the dialog fragment's style to remove the default title and background
        dialog?.window?.setBackgroundDrawableResource(R.drawable.edit_text_box)
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val params = binding.modalLayout.layoutParams
        params.height = (screenHeight * 0.9).toInt()
        params.width = (screenWidth * 0.8).toInt()

        binding.modalLayout.layoutParams = params
        binding.applicationSubmitBtn.setOnClickListener {
            val inputText = binding.motivationTxt.text.toString().trim()
            if (inputText.isEmpty()) {
                // Show an error
                binding.motivationTxt.error = "This field is required"
            } else {
                // Proceed with valid input
                listener?.onDialogResult(binding.motivationTxt.text.toString()) // Pass result to listener
                dismiss() // Close the dialog
            }
        }
        // Inflate the custom layout for the dialog
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the parent context implements the listener
        listener = context as? DialogListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false) // Prevent dismissal on outside touch
        return dialog
    }
}