package com.example.nanopost.presentation.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentPostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post) {

    private val binding by viewBinding(FragmentPostBinding::bind)
    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.placeholder0.visibility = View.GONE
        binding.placeholder1.visibility = View.GONE
        binding.placeholder2.visibility = View.GONE
        binding.placeholder3.visibility = View.GONE

        viewModel.getPost(args.postId)

        viewModel.deleteResponseLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().popBackStack()
            }
        }

        viewModel.postLiveData.observe(viewLifecycleOwner) { post ->
            if (post != null) {
                if (post.owner.avatarUrl == null) {
                    post.owner.displayName?.get(0).let {
                        binding.letterAvatar.text = it.toString()
                        binding.letterAvatar.visibility = View.VISIBLE
                    }
                } else {
                    binding.avatar.load(post.owner.avatarUrl)
                    binding.letterAvatar.visibility = View.GONE
                }
                binding.dateTimeField.text = post.dateCreated
                binding.displayNameField.text = post.owner.displayName
                if (post.text != null) {
                    binding.postTextField.text = post.text
                    binding.postTextField.visibility = View.VISIBLE
                } else {
                    binding.postTextField.visibility = View.GONE
                }
                binding.likeButton.text = post.likes.likesCount.toString()

                post.images.getOrNull(0)?.sizes?.getOrNull(0)?.url?.let {
                    binding.placeholder0.load(it)
                    binding.placeholder0.visibility = View.VISIBLE
                }
                post.images.getOrNull(1)?.sizes?.getOrNull(0)?.url?.let {
                    binding.placeholder1.load(it)
                    binding.placeholder1.visibility = View.VISIBLE
                }
                post.images.getOrNull(2)?.sizes?.getOrNull(0)?.url?.let {
                    binding.placeholder2.load(it)
                    binding.placeholder2.visibility = View.VISIBLE
                }
                post.images.getOrNull(3)?.sizes?.getOrNull(0)?.url?.let {
                    binding.placeholder3.load(it)
                    binding.placeholder3.visibility = View.VISIBLE
                }

                binding.avatar.setOnClickListener {
                    val action = PostFragmentDirections.actionPostFragmentToProfileFragment(post.owner.id)
                    findNavController().navigate(action)
                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_button -> {
                    viewModel.deletePost()

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