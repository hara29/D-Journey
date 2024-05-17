package com.dicoding.cindy.storyapp.view.main.liststory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.cindy.storyapp.R
import com.dicoding.cindy.storyapp.data.response.story.ListStoryItem
import com.dicoding.cindy.storyapp.databinding.FragmentListStoryBinding
import com.dicoding.cindy.storyapp.view.main.ListStoryViewModel
import com.dicoding.cindy.storyapp.data.Result
import com.dicoding.cindy.storyapp.view.ViewModelFactory

class ListStoryFragment : Fragment() {
    private var _binding:FragmentListStoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ListStoryViewModel

    private val adapter = StoryAdapter(object : StoryAdapter.OnItemClickCallback {
        override fun onItemClicked(story: ListStoryItem) {
//            val moveDataIntent = Intent(this@MainActivity, DetailActivity::class.java)
//            moveDataIntent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
//            moveDataIntent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
//            startActivity(moveDataIntent)
        }
    })
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListStoryBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory.getInstance(requireContext())).get(ListStoryViewModel::class.java)


//        viewModel.getSession().observe(viewLifecycleOwner) { user ->
//            Log.d("ListFragment", "Nama: ${user.name}")
//            Log.d("ListFragment", "Token: ${user.token}")
//            Log.d("ListFragment", "isLogin: ${user.isLogin}")
//            if (!user.isLogin) {
//                startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
//            }
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        getStories()
        Log.d("ListFragmet", "List fragmet berhasil dijalankan")

    }

    private fun getStories() {
        viewModel.getStories().observe(viewLifecycleOwner){
            if (it != null){
                when(it){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        setStories(it.data.listStory)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showToast(it.error)
                        Log.d("List Story", it.error)
                    }
                }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}