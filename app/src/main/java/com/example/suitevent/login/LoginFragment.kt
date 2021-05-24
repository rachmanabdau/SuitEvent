package com.example.suitevent.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.suitevent.R
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
        observePalindromeResult()

        binding.nextButton.setOnClickListener {
            viewmodel.login(getUsername())
        }
        binding.checkButton.setOnClickListener {
            viewmodel.checkPalinDrome(getPalindromeText())
        }

        return binding.root
    }

    private fun observeLoginResult() {
        viewmodel.snackBarMessage.observe(viewLifecycleOwner, EventObserver { result ->
            if (result != 0) {
                binding.usernameText.error = getString(result)
            } else {
                binding.usernameText.error = null
                navigateToHome()
            }
        })
    }

    private fun observePalindromeResult() {
        viewmodel.isPalindrome.observe(viewLifecycleOwner, EventObserver { result ->
            showDialog(getString(result))
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

    private fun getPalindromeText(): String {
        return binding.palindromeText.text.toString()
    }

    private fun showDialog(message: String) {
        val builder: AlertDialog.Builder = requireActivity().let {
            AlertDialog.Builder(it)
        }

        builder.setMessage(message)
            .setTitle(getString(R.string.palindrome_result))

        builder.apply {
            setPositiveButton(getString(android.R.string.ok)) { dialog, id ->
                dialog.dismiss()
            }
        }


        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}