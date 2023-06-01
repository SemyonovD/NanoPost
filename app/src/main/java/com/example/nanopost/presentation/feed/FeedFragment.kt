package com.example.nanopost.presentation.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment: Fragment(R.layout.fragment_feed) {

    private val binding by viewBinding (FragmentFeedBinding::bind)
    private val viewModel: FeedViewModel by viewModels()
    private val feedAdapter = FeedAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getFeed()

        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = StaggeredGridLayoutManager(1,1)
        binding.recycler.adapter = feedAdapter.apply {
            onItemClick = {
                val action = FeedFragmentDirections.actionFeedFragmentToPostFragment(it)
                findNavController().navigate(action)
            }
        }

        viewModel.feedLiveData.observe(viewLifecycleOwner) { list ->
            feedAdapter.submitData(viewLifecycleOwner.lifecycle,list)
        }

        binding.addPostButton.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }


    }
}