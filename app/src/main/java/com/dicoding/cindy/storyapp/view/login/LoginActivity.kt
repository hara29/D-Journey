package com.dicoding.cindy.storyapp.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.response.login.LoginResponse
import com.dicoding.cindy.storyapp.data.response.login.LoginResult
import com.dicoding.cindy.storyapp.databinding.ActivityLoginBinding
import com.dicoding.cindy.storyapp.view.ViewModelFactory
import com.dicoding.cindy.storyapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.loginUser(email, password).observe(this@LoginActivity){
                if (it != null){
                    when(it){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            loginProcess(it.data)
//                               if (it.data.error) {
//                                   showToast(getString(R.string.login_failed_message))
//                               } else {
//                            viewModel.saveSession(it.data.loginResult)
//                            showToast(getString(R.string.login_success_message))
//                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                               }
                        }
                        is Result.Error -> {
                            showLoading(false)
                            showToast(it.error)
                               // Toast.makeText(this@LoginActivity, it.error, Toast.LENGTH_LONG).show()
                            Log.d("signup act", it.error)
                        }
                    }
                }
            }
        }
    }

    private fun loginProcess(data : LoginResponse){
        viewModel.saveSession(data.loginResult)
        showToast(getString(R.string.login_success_message))
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}