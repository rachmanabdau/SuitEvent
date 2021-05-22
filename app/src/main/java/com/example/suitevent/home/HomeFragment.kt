package com.example.suitevent.home

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.suitevent.databinding.FragmentHomeBinding


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

        binding.eventButton.setOnClickListener {
            navigateToEvent()
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

    private fun navigateToEvent() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToEventFragment()
        )
    }

    private fun observeEventResult() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            eventKeyResult
        )?.observe(viewLifecycleOwner) { result ->
            binding.eventButton.text = result
        }

    }

    companion object {
        const val eventKeyResult = "com.example.suitevent.home.HomeFragment.eventKeyResult"
    }

}