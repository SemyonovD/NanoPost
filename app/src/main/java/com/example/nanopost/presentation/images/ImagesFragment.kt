package com.example.nanopost.presentation.images

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentImagesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment: Fragment(R.layout.fragment_images) {

    private val binding by viewBinding (FragmentImagesBinding::bind)
    private val viewModel: ImagesViewModel by viewModels()
    private val imagesAdapter = ImagesAdapter()
    private val args: ImagesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getImages(args.profileId)

        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = StaggeredGridLayoutManager(3,1)
        binding.recycler.adapter = imagesAdapter.apply {
            onItemClick = {
                val action = ImagesFragmentDirections.actionImagesFragmentToBigImageFragment(it)
                findNavController().navigate(action)
            }
        }

        viewModel.imagesLiveData.observe(viewLifecycleOwner) { list ->
            imagesAdapter.submitData(viewLifecycleOwner.lifecycle,list)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = ImagesFragmentDirections.actionImagesFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }
}