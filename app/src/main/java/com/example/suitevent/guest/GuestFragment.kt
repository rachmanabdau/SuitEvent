package com.example.suitevent.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mymoviddb.adapters.GuestAdapter
import com.example.suitevent.databinding.GuestFragmentBinding
import com.example.suitevent.home.HomeFragment
import com.example.suitevent.model.GuestResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GuestFragment : Fragment() {

    private lateinit var binding: GuestFragmentBinding

    private val viewModel by viewModels<GuestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GuestFragmentBinding.inflate(layoutInflater, container, false)
        setupAdapter()

        return binding.root
    }

    private fun setupAdapter() {
        val adapter = GuestAdapter().also { it.setItemClick { data -> sendResultToHome(data) } }
        binding.guestRv.adapter = adapter.also { guestAdapter ->
            lifecycleScope.launch {
                guestAdapter.loadStateFlow.collectLatest { loadState ->
                    if (loadState.refresh is LoadState.Error) {
                        binding.errorLayout.errorMessage.text =
                            (loadState.refresh as LoadState.Error).error.localizedMessage
                    }
                    binding.errorLayout.root.isVisible = loadState.refresh is LoadState.Error
                    binding.errorLayout.tryAgainButton.setOnClickListener { guestAdapter.retry() }
                }
            }
        }
        populateDate(adapter)
    }

    private fun populateDate(adapter: GuestAdapter) {
        viewModel.getGuestList()
        viewModel.guestResult.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun sendResultToHome(data: GuestResponse.Result) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            HomeFragment.guestKeyResult,
            data
        )
        findNavController().popBackStack()
    }
}