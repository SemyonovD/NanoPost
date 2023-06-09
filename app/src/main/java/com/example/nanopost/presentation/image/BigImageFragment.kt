package com.example.nanopost.presentation.image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentBigImageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BigImageFragment: Fragment(R.layout.fragment_big_image) {

    private val binding by viewBinding(FragmentBigImageBinding::bind)
    private val viewModel: BigImageViewModel by viewModels()
    private val args: BigImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getImage(args.imageId)

        viewModel.deleteResponseLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().popBackStack()
            }
        }

        viewModel.imageLiveData.observe(viewLifecycleOwner) { image ->
            if (image != null) {
                val image1 = image.sizes[0].url
                binding.placeholder.load(image1)
                binding.avatar.load(image.owner.avatarUrl)
                binding.displayNameField.text = image.owner.displayName
                binding.dateTimeField.text = image.dateCreated
                if (image.owner.avatarUrl == null) {
                    image.owner.displayName?.get(0).let {
                        binding.letterAvatar.text = it.toString()
                    }
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_button -> {
                    viewModel.deleteImage()
                    true
                }
                else -> false
            }
        }
    }
}