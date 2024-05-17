package com.dicoding.cindy.storyapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.databinding.ActivityMainBinding
import com.dicoding.cindy.storyapp.view.ViewModelFactory
import com.dicoding.cindy.storyapp.view.main.liststory.ListStoryFragment
import com.dicoding.cindy.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            Log.d("MainAct", "Nama: ${user.name}")
            Log.d("MainAct", "Token: ${user.token}")
            Log.d("MainAct", "isLogin: ${user.isLogin}")
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        val fragmentManager = supportFragmentManager
        val listStoryFragment = ListStoryFragment()
        val fragment = fragmentManager.findFragmentByTag(ListStoryFragment::class.java.simpleName)

        if (fragment !is ListStoryFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + ListStoryFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, listStoryFragment, ListStoryFragment::class.java.simpleName)
                .commit()
        }

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
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

}