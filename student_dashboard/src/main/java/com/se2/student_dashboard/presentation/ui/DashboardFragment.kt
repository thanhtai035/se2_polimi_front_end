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
import com.se2.student_dashboard.presentation.adapter.InternshipAdapter
import com.se2.student_dashboard.presentation.viewmodel.DashboardViewModel
import org.koin.android.ext.android.inject
import java.util.Locale

class DashboardFragment : Fragment(R.layout.fragment_student_dashboard) {
    lateinit var binding: FragmentStudentDashboardBinding
    private val viewModel: DashboardViewModel by inject()
    private var fragmentListener: FragmentListener? = null
    private lateinit var listApplication: ArrayList<Application>
    lateinit var originalInternshipList: List<Internship>
    private val internshipList = mutableListOf<Internship>()
    private lateinit var adapter: InternshipAdapter
    private var keyword: String = ""
    private var location: String = ""
    private var userID = 0
    private var userRole = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentDashboardBinding.inflate(layoutInflater) //initializing the binding class
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userID = arguments?.getInt("userID")!!
        userRole = arguments?.getInt("userRole")!!
        listApplication = (arguments?.getSerializable("application") as? ArrayList<Application>)!!
        viewModel.initialize()

        initPlaceSuggestion()
        if (userRole != 2) {
            binding.editTextText.visibility = View.GONE
            binding.companyTitleTxt.visibility = View.VISIBLE
        }


        binding.addApplicationBtn.setOnClickListener {
            val intent = Intent(context, AddInternshipActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
        binding.editTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                // This will be called when the text is being changed
                keyword = charSequence.toString()
                internshipList.clear()
                internshipList.addAll(searchInternships())
                adapter.notifyDataSetChanged() // Notify the adapter that the data has changed
            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })
        viewModel.internshipList.observe(viewLifecycleOwner) {
                response ->
            when(response) {
                is Result.Success -> {
                    val data = response.data?.reversed()!!
                    if (userRole == 2) {
                        originalInternshipList = data
                        internshipList.addAll(originalInternshipList)
                    } else {
                        originalInternshipList = filterInternshipsByCompanyId(data, userID)
                        internshipList.addAll(originalInternshipList)
                    }
                    initInternship()
                }
                is Result.Loading -> {

                } else -> {

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

    private fun initPlaceSuggestion() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        Places.initialize(requireContext().applicationContext, Constant.GOOGLE_API, Locale.ENGLISH);
        val autoCompleteview = autocompleteFragment.view
        if(userRole != 2) {
            autoCompleteview?.visibility = View.GONE
        }
        autoCompleteview?.setBackgroundResource(R.drawable.white_bg)
        // Specify the types of place data to return.
        autocompleteFragment.view?.findViewById<ImageView>(com.google.android.libraries.places.R.id.places_autocomplete_search_button)?.setImageResource(R.drawable.baseline_location_on_24)
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS))
        autocompleteFragment.setHint("Choose a place")
        autocompleteFragment.view?.setBackgroundResource(R.drawable.white_bg)
        autocompleteFragment.setTypesFilter(listOf(PlaceTypes.LOCALITY)) // Filter only localities (cities)
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                location = place.name.toString()
                internshipList.clear()
                internshipList.addAll(searchInternships())
                adapter.notifyDataSetChanged() // Notify the adapter that the data has changed
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("taitest", "An error occurred: $status")
            }
        })
    }

    private fun initInternship() {
        try {
            binding.progressBarSuggest.visibility = View.VISIBLE
            binding.recyclerViewSuggest.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = InternshipAdapter(internshipList, userID, userRole, listApplication)
            binding.recyclerViewSuggest.adapter = adapter
            binding.progressBarSuggest.visibility = View.GONE
        } catch (e: Exception) {
            Log.d("taitest", e.message.toString())
        }
    }


    fun searchInternships(
    ): List<Internship> {
        // Convert location and keyword to lower case for case-insensitive comparison
        val lowerCaseLocation = location.lowercase()
        val lowerCaseKeyword = keyword.lowercase()

        return originalInternshipList.filter { internship ->
            // Check if the company address contains the location and if the requiredSkills contains the keyword
            val locationMatches = internship.company.address.lowercase().contains(lowerCaseLocation)
            val keywordMatches = internship.requiredSkills?.lowercase()?.contains(lowerCaseKeyword)
            val keywordMatches2 = internship.title?.lowercase()?.contains(lowerCaseKeyword)

            locationMatches && (keywordMatches == true || keywordMatches2 == true)
        }
    }

    fun filterInternshipsByCompanyId(internships: List<Internship>, targetId: Int?): List<Internship> {
        return internships.filter { it.company.id == targetId }
    }
}
