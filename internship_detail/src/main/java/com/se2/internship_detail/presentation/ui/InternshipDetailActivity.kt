package com.se2.internship_detail.presentation.ui

import Internship
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.se2.base.Common.Result
import com.se2.base.ext.FragmentListener
import com.se2.internship_detail.data.dto.ApplicationDto
import com.se2.internship_detail.data.dto.InternshipRefDto
import com.se2.internship_detail.data.dto.StudentRefDto
import com.se2.internship_detail.databinding.InternshipDetailActivityBinding
import com.se2.internship_detail.presentation.viewmodel.InternshipDetailViewModel
import com.tyme.base.Common.FragmentEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class InternshipDetailActivity : AppCompatActivity(), ModalFragment.DialogListener {
    lateinit var binding: InternshipDetailActivityBinding
    private val viewModel: InternshipDetailViewModel by viewModel()
    private var fragmentListener: FragmentListener? = null // Call to Navigation Activity to change screen
    private lateinit var item: Internship
    var studentID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InternshipDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        setupViewPager()
    }


    private fun getBundle() {
        item = intent.getSerializableExtra("object") as Internship
        studentID = intent.getIntExtra("userID", 0) as Int
        val applied = intent.getBooleanExtra("applied", false)
        binding.titleTxt.text = item.title
        binding.companyTxt.text = item.company.fullName
        binding.locationTxt.text = item.company.address
        binding.jobTypeTxt.text = item.type
        binding.salaryTxt.text = item.salary

        if (applied) {binding.applyJobBtn.visibility = View.GONE}
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.applyJobBtn.setOnClickListener {
            val modalDialogFragment = ModalFragment()
            modalDialogFragment.show(supportFragmentManager, "modalDialog")
        }
    }

    override fun onStart() {
        // Check Log in Response
        viewModel.sendApplicationResponse.observe(this) {
                response ->
            when (response) {
                is Result.Success -> {
                    if (response.data!!) {
                        val resultIntent = Intent()
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                        Toast.makeText(this, "Send Application Successfully", Toast.LENGTH_LONG)
                            .show()
                    }  else {
                        Toast.makeText(this, "Error in sending the application", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                // On Loading - Open dialog with loading progress view
                is Result.Loading -> {
                }
                // Error - Change dialog to error message
                else -> {
                    Log.d("taitest", response.message.toString())
                    Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_SHORT).show()

                }
            }
        }
        super.onStart()
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val tab1 = AboutFragment()
        val tab2 = CompanyFragment()

        val bundle1 = Bundle()
        bundle1.putString("description", item.description)
        bundle1.putString("requiredSkills", item.requiredSkills)
        bundle1.putString("optionalSkills", item.optionalSkills)

        val bundle2 = Bundle()
        bundle2.putString("company", item.company.aboutYou)
        tab1.arguments = bundle1
        tab2.arguments = bundle2

        adapter.addFrag(tab1, "About")
        adapter.addFrag(tab2, "Company")

        binding.viewpager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }

    private class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragmentList = arrayListOf<Fragment>()
        private val fragmentTitleList = arrayListOf<String>()

        override fun getCount(): Int = fragmentList.size

        override fun getItem(position: Int): Fragment = fragmentList[position]
        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence = fragmentTitleList[position]
    }

    override fun onDialogResult(result: String) {
        viewModel.sendApplication(
            ApplicationDto(
                internship = InternshipRefDto(id = item.id),
                motivationText = result,
                status = "Applied",
                student = StudentRefDto(id = studentID?:0)
            )
        )
    }
}