package com.se2.students_and_companies.app.presentation.authentication

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.se2.students_and_companies.R
import com.se2.students_and_companies.app.presentation.authentication.viewmodel.AuthenticationViewModel
import com.se2.base.presentation.activity.BaseActivity
import com.se2.base.Common.Result
import com.se2.students_and_companies.app.presentation.navigation.ui.NavigationActivity
import com.se2.students_and_companies.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInActivity : BaseActivity(R.layout.activity_login) {
    private val viewModel: AuthenticationViewModel by viewModel()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Push edit text programmatically
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityLoginBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)


        // Set Log in button listener
        binding.loginbutton.setOnClickListener {
            viewModel.logIn(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
        }

        binding.signupTxt.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        // Check Log in Response
            viewModel.response.observe(this) {
                    response ->
                when (response) {
                    is Result.Success -> {
                        val intent = Intent(this, NavigationActivity::class.java)
                        val user = response.data
                        intent.putExtra("id", user?.id)
                        intent.putExtra("role", user?.role)
                        startActivity(intent)
                        finish()
                    }
                        // On Loading - Open dialog with loading progress view
                    is Result.Loading -> {

                    }
                        // Error - Change dialog to error message
                    else -> {
                        Toast.makeText(this, "Invalid Input or Network Error", Toast.LENGTH_LONG).show()
                    }
            }
        }
        super.onStart()
    }
}