package com.dicoding.cindy.storyapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.data.response.story.ListStoryItem
import com.dicoding.cindy.storyapp.databinding.ActivityMainBinding
import com.dicoding.cindy.storyapp.view.ViewModelFactory
import com.dicoding.cindy.storyapp.view.main.addstory.AddStoryActivity
import com.dicoding.cindy.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val adapter = StoryAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupView()
        setupAction()
        getSession()

        binding.fabAdd.setOnClickListener{
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getSession()
    }

    private fun getSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                getStories(user.token)
            }
        }
    }

    private fun getStories(token: String) {
        viewModel.getStories(token).observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    setStories(result.data.listStory)
                }
                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                    Log.d("List Story", result.error)
                }
            }
        }
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
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            // addItemDecoration(DividerItemDecoration(this@MainActivity, (layoutManager as LinearLayoutManager).orientation))
            setHasFixedSize(true)
            adapter = adapter
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_logout -> {
                    showLogoutConfirmationDialog()
                    true
                }
                R.id.menu_setting -> {
//                    val intent = Intent(this@MainActivity, SettingActivity::class.java)
//                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun setStories(stories: List<ListStoryItem>) {
        if (stories.isEmpty()) {
            showToast(getString(R.string.empty_stories))
        } else {
            adapter.submitList(stories)
            binding.recyclerView.adapter = adapter
        }
    }
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.logout))
        builder.setMessage(getString(R.string.logout_alert_message))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            viewModel.logout()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
            // User clicked No button, do nothing
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}