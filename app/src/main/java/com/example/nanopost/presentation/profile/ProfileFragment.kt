package com.example.nanopost.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.getProfile(args.profileId)

        super.onViewCreated(view, savedInstanceState)

        viewModel.profileLiveData.observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                viewModel.ourProfileIdLiveData.observe(viewLifecycleOwner) { string ->
                    val postAdapter = PostAdapter()
                    val headerAdapter = HeaderAdapter(profile, string)
                    val concatAdapter = ConcatAdapter(headerAdapter, postAdapter)

                    binding.recycler.layoutManager = StaggeredGridLayoutManager(1, 1)
                    binding.recycler.adapter = concatAdapter.apply {
                        postAdapter.onPostClick = {
                            val action =
                                ProfileFragmentDirections.actionProfileFragmentToPostFragment(it)
                            findNavController().navigate(action)
                        }
                        headerAdapter.onEditClick = { list ->
                            viewModel.editProfile(list[0],list[1],list[2],null)
                        }
                        headerAdapter.onSubscribeClick = { string ->
                            viewModel.subscribeProfile(string)
                        }
                        headerAdapter.onUnsubscribeClick = { string ->
                            viewModel.unsubscribeProfile(string)

                        }
                        headerAdapter.onImagesClick = { string ->
                            val action = ProfileFragmentDirections.actionProfileFragmentToImagesFragment(string)
                            findNavController().navigate(action)
                        }
                    }

                    viewModel.postsLiveData.observe(viewLifecycleOwner) { list ->
                        postAdapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }

                }
            }
        }

        binding.addPostButton.setOnClickListener{
            val action = ProfileFragmentDirections.actionProfileFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.exit_btn -> {
                    context?.let {
                        MaterialAlertDialogBuilder(it)
                            .setTitle("Logout")
                            .setMessage("Are you sure you want to log out?")
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.cancel()
                            }
                            .setPositiveButton("Confirm") { _, _ ->
                                viewModel.logout()
                                findNavController().navigate(
                                    ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                                )
                            }
                            .show()
                    }
                    true
                }
                else -> false
            }
        }
    }
}
