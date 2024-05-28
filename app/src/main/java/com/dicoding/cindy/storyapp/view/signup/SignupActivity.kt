package com.dicoding.cindy.storyapp.view.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.databinding.ActivitySignupBinding
import com.dicoding.cindy.storyapp.view.ViewModelFactory
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.view.login.LoginActivity


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        with(binding){
            signupButton.setOnClickListener {
                val email = edRegisterEmail.text.toString()
                val name = edRegisterName.text.toString()
                val password = edRegisterPassword.text.toString()

                viewModel.signUpUser(name, email, password).observe(this@SignupActivity) {
                    if (it != null) {
                        when(it) {
                            is Result.Loading -> {
                                showLoading(true)
                            }
                            is Result.Success -> {
                                showLoading(false)
                                if (it.data.error) {
                                    showToast(getString(R.string.signup_failed_message))
                                } else {
                                    showToast(getString(R.string.signup_success_message))
                                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                            is Result.Error -> {
                                showLoading(false)
                                Toast.makeText(this@SignupActivity, it.error, Toast.LENGTH_LONG).show()
                                Log.d("signup act", it.error)
                            }

                        }
                    }
                }
            }
        }

    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}