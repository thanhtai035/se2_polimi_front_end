package com.se2.internship_detail.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.se2.internship_detail.R
import com.se2.internship_detail.databinding.FragmentAboutBinding
import com.se2.internship_detail.presentation.adapter.SkillAdapter


class AboutFragment : Fragment() {
    private lateinit var requiredSkillList: List<String>
    private lateinit var optionalSkillList: List<String>
    lateinit var binding: FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater) //initializing the binding class
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val descTxt=view.findViewById<TextView>(R.id.descriptionTxt)
        descTxt.text=arguments?.getString("description","")?:""

        val requiredSkillString = arguments?.getString("requiredSkills", "")?:""
        requiredSkillList = splitFormattedString(requiredSkillString)
        val requiredSkillAdapter = SkillAdapter(requiredSkillList)
        binding.requiredSkillsList.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.requiredSkillsList.adapter = requiredSkillAdapter

        val optionalSkillString = arguments?.getString("optionalSkills", "")?:""
        optionalSkillList = splitFormattedString(optionalSkillString)
        val optionalSkillAdapter = SkillAdapter(optionalSkillList)
        binding.optionalSkillsList.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        binding.optionalSkillsList.adapter = optionalSkillAdapter
    }

    fun splitFormattedString(input: String): List<String> {
        return input.split(", ").map { it.trim() }
    }
}