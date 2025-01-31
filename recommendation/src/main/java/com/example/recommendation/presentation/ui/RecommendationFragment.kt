package com.example.recommendation.presentation.ui

import Internship
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recommendation.R
import com.example.recommendation.databinding.ActivityRecommendationBinding
import com.example.recommendation.presentation.adapter.RecommendationAdapter
import com.example.recommendation.presentation.viewmodel.RecommendationViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp

import com.se2.base.Common.Result
import com.tyme.base.Common.FragmentEnum
import com.se2.base.ext.FragmentListener
import com.se2.base.model.Application
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecommendationFragment : Fragment(R.layout.activity_recommendation) {
    lateinit var binding: ActivityRecommendationBinding
    private var fragmentListener: FragmentListener? = null // Call to Navigation Activity to change screen
    private val viewModel: RecommendationViewModel by viewModel()
    lateinit var firstInternshipList: List<Internship>
    lateinit var secondInternshipList: List<Internship>
    private lateinit var listApplication: ArrayList<Application>
    private var isMechanismOne = true
    private var userID = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRecommendationBinding.inflate(layoutInflater) //initializing the binding class
        userID = arguments?.getInt("userID")!!
        listApplication = (arguments?.getSerializable("application") as? ArrayList<Application>)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = arguments?.getInt("userID")!!
        viewModel.getFirstRecommendation(userID)
        viewModel.getSecondRecommendation(userID)

        binding.changeMechanismBtn.setOnClickListener {
            if(isMechanismOne) {
                binding.recyclerViewMechanism1.visibility = View.GONE
                binding.recyclerViewMechanism2.visibility = View.VISIBLE
                binding.mechanismDescTxt.text = "Text-search based with Scoring System And Extra Metrics Mechanism"
                isMechanismOne = false
            } else {
                binding.recyclerViewMechanism2.visibility = View.GONE
                binding.recyclerViewMechanism1.visibility = View.VISIBLE
                binding.mechanismDescTxt.text = "Text-search based with Scoring System Mechanism"
                isMechanismOne = true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.firstRecommendationInternshipList.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    firstInternshipList = response.data?: emptyList()
                    initFirstAdapter()
                }
                is Result.Loading -> {

                } else -> {
                    Toast.makeText(context, "Error fetching the data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.secondRecommendationInternshipList.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    secondInternshipList = response.data?: emptyList()
                    initSecondAdapter()
                }
                is Result.Loading -> {

                } else -> {
                Toast.makeText(context, "Error fetching the data", Toast.LENGTH_SHORT).show()
            }
            }
        }
    }

    fun initFirstAdapter() {
        binding.recyclerViewMechanism1.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val firstRecommendationAdapter = RecommendationAdapter(firstInternshipList.reversed(), userID, listApplication)
        binding.recyclerViewMechanism1.adapter = firstRecommendationAdapter
    }

    fun initSecondAdapter() {
        binding.recyclerViewMechanism2.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val secondRecommendationAdapter = RecommendationAdapter(secondInternshipList.reversed(), userID, listApplication)
        binding.recyclerViewMechanism2.adapter = secondRecommendationAdapter
    }
}
