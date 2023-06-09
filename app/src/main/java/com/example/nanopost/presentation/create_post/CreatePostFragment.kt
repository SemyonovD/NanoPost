package com.example.nanopost.presentation.create_post

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentCreatePostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePostFragment: Fragment(R.layout.fragment_create_post) {

    private val binding by viewBinding(FragmentCreatePostBinding::bind)
    private val viewModel: CreatePostViewModel by viewModels()

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.saveImageUri(uri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImageButton.visibility = View.VISIBLE
        binding.pickedImagesContainers.visibility = View.GONE

        viewModel.postResponseLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().popBackStack()
            }
        }

        viewModel.imageListLiveData.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                if (list.size == 4) {
                    binding.addImageButton.visibility = View.GONE
                } else {
                    binding.addImageButton.visibility = View.VISIBLE
                }
                list.getOrNull(0).let {
                    if (it != null) {
                        binding.pickedImage0.load(it)
                        binding.pickedImagesContainers.visibility = View.VISIBLE
                        binding.pickedImageContainer0.visibility = View.VISIBLE

                    } else {
                        binding.pickedImageContainer0.visibility = View.GONE
                        binding.pickedImagesContainers.visibility = View.GONE
                    }
                }
                list.getOrNull(1).let {
                    if (it != null) {
                        binding.pickedImage1.load(it)
                        binding.pickedImageContainer1.visibility = View.VISIBLE
                    } else {
                        binding.pickedImageContainer1.visibility = View.GONE
                    }

                }
                list.getOrNull(2).let {
                    if (it != null) {
                        binding.pickedImage2.load(it)
                        binding.pickedImageContainer2.visibility = View.VISIBLE
                    } else {
                        binding.pickedImageContainer2.visibility = View.GONE
                    }

                }
                list.getOrNull(3).let {
                    if (it != null) {
                        binding.pickedImage3.load(it)
                        binding.pickedImageContainer3.visibility = View.VISIBLE
                    } else {
                        binding.pickedImageContainer3.visibility = View.GONE
                    }

                }
            }
        }

        binding.addImageButton.setOnClickListener {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.deleteImageButton0.setOnClickListener {
            viewModel.deleteImageUri(0)
        }
        binding.deleteImageButton1.setOnClickListener {
            viewModel.deleteImageUri(1)
        }
        binding.deleteImageButton2.setOnClickListener {
            viewModel.deleteImageUri(2)
        }
        binding.deleteImageButton3.setOnClickListener {
            viewModel.deleteImageUri(3)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ok_button -> {
                    viewModel.createPost(binding.enterTextField.text.toString())
                    //findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}