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

        viewModel.imageUriLiveData.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                binding.pickedImage.load(uri)
            }
        }

        binding.addImageButton.setOnClickListener {
            imagePickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

        binding.deleteImageButton.setOnClickListener {
            viewModel.deleteImageUri()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.ok_button -> {
                    viewModel.createPost(binding.enterTextField.text.toString())
                    findNavController().popBackStack()
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