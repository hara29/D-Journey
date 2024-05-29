package com.dicoding.cindy.storyapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.databinding.ActivityMainBinding
import com.dicoding.cindy.storyapp.view.ViewModelFactory
import com.dicoding.cindy.storyapp.view.main.addstory.AddStoryActivity
import com.dicoding.cindy.storyapp.view.main.maps.MapsActivity
import com.dicoding.cindy.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<ListStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val adapter = StoryAdapter()

    private lateinit var binding: ActivityMainBinding
    private lateinit var token: String

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
                token = user.token
                setStories(token)
            }
        }
    }

    private fun setStories(token: String) {
        binding.recyclerView.adapter = adapter
        viewModel.getStories(token).observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }
    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = adapter
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_logout -> {
                    showLogoutConfirmationDialog()
                    true
                }
                R.id.menu_maps -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    intent.putExtra(MapsActivity.EXTRA_TOKEN, token)
                    startActivity(intent)
                    true
                }
                else -> false
            }
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