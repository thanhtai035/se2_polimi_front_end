package com.se2.students_and_companies.app.presentation.navigation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.recommendation.presentation.ui.RecommendationFragment
import com.se2.account_profile.presentation.ui.ui.presentation.ui.ProfileFragment
import com.se2.base.Common.Result
import com.se2.student_dashboard.presentation.ui.DashboardFragment
import com.se2.students_and_companies.R
import com.se2.students_and_companies.databinding.ActivityNavigationBinding
import com.tyme.base.Common.FragmentEnum
import com.se2.base.ext.FragmentListener
import com.se2.base.model.Application
import com.se2.students_and_companies.app.data.dto.StudentApplicationHolderDto
import com.se2.students_and_companies.app.data.dto.UserID
import com.se2.students_and_companies.app.presentation.navigation.viewmodel.NavigationViewModel
import com.se2.base.presentation.activity.BaseActivity
import com.se2.student_application.presentation.ui.ApplicationFragment
import com.se2.student_dashboard.presentation.ui.UniversityDashboardFragment
import com.se2.students_and_companies.app.presentation.authentication.LogInActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant
import java.time.format.DateTimeFormatter


class NavigationActivity : BaseActivity(R.layout.activity_navigation), FragmentListener {
    private lateinit var binding: ActivityNavigationBinding
    private var userID: Int = 0
    private var userRole: Int = 0
    private var userName: String = ""
    private val viewModel: NavigationViewModel by viewModel()
    private lateinit var listApplication: ArrayList<Application>
    private var lastScreen: FragmentEnum = FragmentEnum.StudentDashboard
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        // Initialize Binding
        userID = intent.getIntExtra("id", 0)
        userRole = intent.getIntExtra("role", 0)
        userName = intent.getStringExtra("name")?:"Politecnico Di Milano"

        if (count == 0) {
            if (userRole == 2 || userRole == 4) {
                viewModel.getStudentApplication(StudentApplicationHolderDto(UserID(userID)))
            } else if (userRole == 3) {
                viewModel.getCompanyApplication(userID.toString())
            }
        }

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (userRole != 2) {
            binding.navigationView.menu.findItem(R.id.recommendation).isVisible = false
            binding.navigationView.menu.findItem(R.id.application).isVisible = false
        }
        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.account -> {
                    onNavigate(FragmentEnum.Profile)
                    true
                }

                R.id.dashboard -> {
                    onNavigate(FragmentEnum.StudentDashboard)
                    true
                }

                R.id.recommendation -> {
                    onNavigate(FragmentEnum.Recommendation)
                    true
                }
                R.id.application -> {
                    onNavigate(FragmentEnum.Application)
                    true
                }
                else -> {false}
            }
        }
    }

    // Callback navigation from fragment
    override fun onNavigate(fragment: FragmentEnum) {
        when (fragment) {
            FragmentEnum.StudentDashboard -> {
                if (userRole != 4) {
                    lastScreen = FragmentEnum.StudentDashboard
                    val dashboardFragment = DashboardFragment()
                    var bundle = Bundle()
                    bundle.putInt("userID", userID)
                    bundle.putInt("userRole", userRole)
                    if (userRole == 2) {
                        bundle.putSerializable(
                            "application",
                            ArrayList(filterAndSortApplicationsByStudentId(listApplication, userID))
                        )
                    } else if (userRole == 3) {
                        bundle.putSerializable(
                            "application",
                            ArrayList(filterAndSortApplicationsByCompanyId(listApplication, userID))
                        )
                    }
                    dashboardFragment.arguments = bundle
                    replaceFragment(dashboardFragment)
                } else {
                    val bundle = Bundle()
                    bundle.putString("uniName", userName)
                    bundle.putSerializable(
                        "application",
                        ArrayList(filterAndSortApplicationsByStudentId(listApplication, userID))
                    )
                    val uniFragment =  UniversityDashboardFragment()
                    uniFragment.arguments = bundle
                    replaceFragment(uniFragment)
                }
            }
            FragmentEnum.Profile -> {
                lastScreen = FragmentEnum.Profile
                val bundle = Bundle()
                bundle.putInt("userID", userID)
                val fragment = ProfileFragment()
                fragment.arguments = bundle
                replaceFragment(fragment)
            }
            FragmentEnum.Signout ->  {
                val intent = Intent(this, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }
            FragmentEnum.Application -> {
                lastScreen = FragmentEnum.Application
                val fragment = ApplicationFragment()
                val bundle = Bundle()
                bundle.putInt("userID", userID)
                bundle.putSerializable("application",ArrayList(filterAndSortApplicationsByStudentId(listApplication, userID)))
                fragment.arguments = bundle
                replaceFragment(fragment)
            }
            FragmentEnum.Recommendation -> {
                lastScreen = FragmentEnum.Recommendation
                val fragment = RecommendationFragment()
                val bundle = Bundle()
                bundle.putInt("userID", userID)
                if (userRole == 2) {
                    bundle.putSerializable("application",ArrayList(filterAndSortApplicationsByStudentId(listApplication, userID)))
                }
                fragment.arguments = bundle
                replaceFragment(fragment)
            }
            FragmentEnum.REFRESH -> {
                if (userRole == 2) {
                    viewModel.getStudentApplication(StudentApplicationHolderDto(UserID(userID)))
                } else if (userRole == 3) {
                    viewModel.getCompanyApplication(userID.toString())
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()

        if (userRole == 2) {
            viewModel.getStudentApplication(StudentApplicationHolderDto(UserID(userID)))
        } else if (userRole == 3) {
            viewModel.getCompanyApplication(userID.toString())
        }
    }


    override fun onStart() {
        // Check Log in Response
        viewModel.listApplicationResponse.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    if (count == 0) {
                        listApplication = ArrayList(response?.data?:emptyList())
                        binding.navigationView.selectedItemId = R.id.dashboard
                        count++
                    } else {
                        listApplication = ArrayList(response?.data?:emptyList())
                        onNavigate(lastScreen)
                    }
                }
                // On Loading - Open dialog with loading progress view
                is Result.Loading -> {
                }
                // Error - Change dialog to error message
                else -> {
                    Toast.makeText(this, "Error in getting Application data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onStart()
    }

    override fun getUserID(): Int {
        return userID
    }

    override fun getUserRole(): Int {
        return userRole
    }

    // Method to change fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navHostFragment, fragment)
        fragmentTransaction.commit()
    }

    fun filterAndSortApplicationsByStudentId(
        applications: List<Application>,
        targetId: Int
    ): ArrayList<Application> {
        // Filter by id
        val filteredList = applications.filter { it.student?.id == targetId }

        // Sort by createdAt
        val sortedList = filteredList.sortedByDescending {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = Instant.from(formatter.parse(it.createdAt))  // Parse string to Instant
            dateTime
        }

        // Convert to ArrayList
        return ArrayList(sortedList)
    }

    fun filterAndSortApplicationsByCompanyId(
        applications: List<Application>,
        targetId: Int
    ): ArrayList<Application> {
        // Filter by id
        val filteredList = applications.filter { it.internship?.company?.id == targetId }

        // Sort by createdAt
        val sortedList = filteredList.sortedByDescending {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val dateTime = Instant.from(formatter.parse(it.createdAt))  // Parse string to Instant
            dateTime
        }

        // Convert to ArrayList
        return ArrayList(sortedList)
    }

    // Helper function to parse ISO-8601 date strings
    fun parseDate(dateString: String): Long {
        return try {
            val formatter = java.time.format.DateTimeFormatter.ISO_INSTANT
            java.time.Instant.from(formatter.parse(dateString)).toEpochMilli()
        } catch (e: Exception) {
            0L // Fallback to 0 if parsing fails
        }
    }
}