package com.example.suitevent.event

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.suitevent.R
import com.example.suitevent.adapter.EventAdapter
import com.example.suitevent.databinding.FragmentEventBinding
import com.example.suitevent.home.HomeFragment
import com.example.suitevent.model.Event
import com.example.suitevent.model.dummyEventList


class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        val adapter = EventAdapter(dummyEventList) { eventItem -> returnEventToHome(eventItem) }
        //adapter.submitList(dummyEventList)
        binding.eventRv.adapter = adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun returnEventToHome(data: Event) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            HomeFragment.eventKeyResult,
            data.name
        )
        findNavController().popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.event_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mapFragment -> findNavController().navigate(
                EventFragmentDirections.actionEventFragmentToMapFragment(
                    dummyEventList.toTypedArray()
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }
}