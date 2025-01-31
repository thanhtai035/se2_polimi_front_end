package com.se2.student_application.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.se2.base.Common.Result
import com.se2.base.model.Application
import com.se2.student_application.R
import com.se2.student_application.databinding.FragmentStudentApplicationBinding
import com.se2.student_application.presentation.adapter.ApplicationAdapter
import com.se2.student_application.presentation.viewmodel.ApplicationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApplicationFragment : Fragment(R.layout.fragment_student_application) {
    lateinit var binding: FragmentStudentApplicationBinding
    private val viewModel: ApplicationViewModel by viewModel()
    private lateinit var listApplication: ArrayList<Application>
    private var userID = 0
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

                }
                is Result.Loading -> {

                } else -> {
                    Toast.makeText(context, "Error fetching the data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun initAdapter() {
        binding.recyclerViewApplication.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val adapter = ApplicationAdapter(listApplication)
        binding.recyclerViewApplication.adapter = adapter
    }

}
