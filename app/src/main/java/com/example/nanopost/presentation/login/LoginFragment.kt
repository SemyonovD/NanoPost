package com.example.nanopost.presentation.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.nanopost.R
import com.example.nanopost.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private val binding by viewBinding (FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.checkLogin()

        super.onViewCreated(view, savedInstanceState)

        binding.passwordInput.visibility = View.GONE
        binding.usernameInput.isEnabled = true

        viewModel.profileIdLiveData.observe(viewLifecycleOwner) { profileId ->
            profileId?.let {
                val action = LoginFragmentDirections.actionLoginFragmentToFeedFragment()
                findNavController().navigate(action)
            }
        }

        viewModel.errorUsernameLiveData.observe(viewLifecycleOwner) { string ->
            string?.let {
                if ((string == "Free" || string == "Taken") && binding.usernameInput.isEnabled) {
                    binding.usernameInput.error = null
                } else {
                    binding.passwordInput.visibility = View.GONE
                    binding.usernameInput.error = string
                }

                binding.continueButton.setOnClickListener {
                    if (
                        binding.usernameField.text != null && binding.usernameInput.isEnabled
                    ) {
                        binding.passwordInput.visibility = View.VISIBLE
                        binding.passwordInput.isEnabled = true
                        binding.usernameInput.isEnabled = false
                    } else if (
                        binding.passwordField.text != null && string == "Free"
                    ) {
                        viewModel.register(
                            binding.usernameField.text.toString(),
                            binding.passwordField.text.toString()
                        )
                    } else if (
                        binding.passwordField.text != null && string  == "Taken"
                    ) {
                        viewModel.login(
                            binding.usernameField.text.toString(),
                            binding.passwordField.text.toString()
                        )
                    }
                }
            }
        }

        binding.usernameField.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().lowercase(Locale.getDefault())
            viewModel.checkUsername(query)
        }
    }
}