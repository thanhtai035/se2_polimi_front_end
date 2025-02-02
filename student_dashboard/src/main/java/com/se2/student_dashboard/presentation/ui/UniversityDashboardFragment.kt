package com.se2.student_dashboard.presentation.ui

import Internship
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.se2.base.Common.Result
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.se2.base.Common.Constant
import com.se2.base.ext.FragmentListener
import com.se2.base.model.Application
import com.se2.student_dashboard.R
import com.se2.student_dashboard.databinding.FragmentStudentDashboardBinding
import com.se2.student_dashboard.databinding.FragmentUniDashboardBinding
import com.se2.student_dashboard.presentation.adapter.InternshipAdapter
import com.se2.student_dashboard.presentation.adapter.StudentAdapter
import com.se2.student_dashboard.presentation.viewmodel.DashboardViewModel
import com.se2.student_dashboard.presentation.viewmodel.UniversityViewModel
import org.koin.android.ext.android.inject
import java.util.Locale

class UniversityDashboardFragment : Fragment(R.layout.fragment_student_dashboard) {
    lateinit var binding: FragmentUniDashboardBinding
    private val viewModel: UniversityViewModel by inject()
    private lateinit var listApplication: ArrayList<Application>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uniName = arguments?.getString("uniName")!!
        viewModel.getStudents(uniName)
        binding = FragmentUniDashboardBinding.inflate(layoutInflater) //initializing the binding class
        listApplication = (arguments?.getSerializable("application") as? ArrayList<Application>)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.response.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    val data = response.data?.reversed()!!
                    binding.studentRecycleView.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    val adapter = StudentAdapter(data, listApplication)
                    binding.studentRecycleView.adapter = adapter                }
                is Result.Loading -> {

                } else -> {

                }
            }
        }
    }

}
