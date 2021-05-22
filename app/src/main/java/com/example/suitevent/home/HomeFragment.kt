package com.example.suitevent.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.suitevent.databinding.FragmentHomeBinding
import com.example.suitevent.model.GuestResponse


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val args by lazy {
        HomeFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setGreetings(args.username)
        observeEventResult()
        observeGuestResult()

        binding.eventButton.setOnClickListener {
            navigateToEvent()
        }

        binding.guestButton.setOnClickListener {
            navigateToGuest()
        }

        return binding.root
    }

    private fun setGreetings(username: String) {
        val greetText = "Hello, "
        val greetUser = greetText + username
        val spannableGreet = SpannableStringBuilder(greetUser)
        spannableGreet.setSpan(
            StyleSpan(Typeface.BOLD),
            greetText.length,
            greetUser.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.greetings.text = spannableGreet
    }

    private fun observeEventResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            eventKeyResult
        )?.observe(viewLifecycleOwner) { result ->
            binding.eventButton.text = result
        }
    }

    private fun observeGuestResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<GuestResponse.Result>(
            guestKeyResult
        )?.observe(viewLifecycleOwner) { result ->
            val fullName = "${result.firstName} ${result.lastName}"
            binding.guestButton.text = fullName
            showPlatformToast(result.id)
        }
    }

    private fun navigateToEvent() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToEventFragment()
        )
    }

    private fun navigateToGuest() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToGuestFragment()
        )
    }

    private fun showPlatformToast(id: Int) {
        Toast.makeText(requireContext(), getPlatform(id), Toast.LENGTH_SHORT).show()
    }

    private fun getPlatform(id: Int): String {
        val isMultiple2 = id % 2 == 0
        val isMultiple3 = id % 3 == 0
        return when {
            isMultiple2 && isMultiple3 -> "iOs"
            id % 2 == 0 -> "blackberry"
            id % 3 == 0 -> "android"
            else -> "feature phones"
        }
    }

    companion object {
        const val eventKeyResult = "com.example.suitevent.home.HomeFragment.eventKeyResult"
        const val guestKeyResult = "com.example.suitevent.home.HomeFragment.guestKeyResult"
    }

}