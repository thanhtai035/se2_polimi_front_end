package com.se2.student_application.presentation.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.se2.base.Common.Result
import com.se2.base.ext.FragmentListener
import com.se2.base.model.Application
import com.se2.student_application.R
import com.se2.student_application.databinding.FragmentStudentApplicationBinding
import com.se2.student_application.presentation.adapter.ApplicationAdapter
import com.se2.student_application.presentation.viewmodel.ApplicationViewModel
import com.tyme.base.Common.FragmentEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApplicationFragment : Fragment(R.layout.fragment_student_application) {
    lateinit var binding: FragmentStudentApplicationBinding
    private val viewModel: ApplicationViewModel by viewModel()
    private lateinit var listApplication: ArrayList<Application>
    private var userID = 0
    private var fragmentListener: FragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentApplicationBinding.inflate(layoutInflater) //initializing the binding class
        userID = arguments?.getInt("userID")!!
        listApplication = (arguments?.getSerializable("application") as? ArrayList<Application>)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = arguments?.getInt("userID")!!
        initAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewModel.response.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    fragmentListener?.onNavigate(FragmentEnum.Application)
                    Toast.makeText(context, "Update status successfully", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {

                } else -> {
                    Toast.makeText(context, "Error updating the status", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

    fun initAdapter() {
        binding.recyclerViewApplication.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val adapter = ApplicationAdapter(listApplication) {
            item ->
            AlertDialog.Builder(context)
                .setTitle("Accept the application")
                .setMessage("Do you want to proceed with the internship")
                .setPositiveButton("Accept") { _, _ ->
                    item.id?.let { viewModel.updateApplication(it, "In Progress") }
                }
                .setNegativeButton("Reject") { _, _ ->
                }
                .show()
        }
        binding.recyclerViewApplication.adapter = adapter
    }

}
