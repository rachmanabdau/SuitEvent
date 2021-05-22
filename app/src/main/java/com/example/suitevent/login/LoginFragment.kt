package com.example.suitevent.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.suitevent.databinding.FragmentLoginBinding
import com.example.suitevent.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewmodel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        observeLoginResult()
        binding.nextButton.setOnClickListener {
            login()
        }

        return binding.root
    }

    private fun login() {
        viewmodel.login(getUsername())
    }

    private fun observeLoginResult() {
        viewmodel.snackBarMessage.observe(viewLifecycleOwner, EventObserver { result ->
            if (result != 0) {
                binding.usernameInput.error = getString(result)
            } else {
                binding.usernameInput.error = null
                navigateToHome()
            }
        })
    }

    private fun navigateToHome() {
        findNavController().navigate(
            LoginFragmentDirections.actionLandingFragmentToHomeFragment(getUsername())
        )
    }

    private fun getUsername(): String {
        return binding.usernameText.text.toString()
    }

}