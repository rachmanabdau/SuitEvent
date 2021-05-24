package com.example.suitevent.map

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.suitevent.R
import com.example.suitevent.adapter.EventAdapter
import com.example.suitevent.databinding.FragmentMapBinding
import com.example.suitevent.model.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding

    private val mapArg by navArgs<MapFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        val mapFragment = SupportMapFragment.newInstance()
        parentFragmentManager
            .beginTransaction()
            .add(binding.map.id, mapFragment)
            .commit()

        mapFragment.getMapAsync(this)

        binding.eventPager.adapter = EventAdapter(mapArg.locationList.toList()) {}
            .setAdapterForMaps()
        binding.eventPager.offscreenPageLimit = 1
        binding.eventPager.setCurrentItem(0, true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.event_map_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.eventFragment -> findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val eventList = mapArg.locationList.toList()
        addMarker(googleMap, eventList)

        binding.eventPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                googleMap.clear()
                addMarker(googleMap, eventList, position)
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f))
            }
        })

    }

    fun addMarker(
        googleMap: GoogleMap,
        eventList: List<Event>,
        position: Int = 0
    ) {
        for (map in eventList) {
            val markerIcon = if (map.id - 1 == position) R.drawable.ic_marker_selected
            else R.drawable.ic_marker_unselected
            // below line is use to add marker to each location of our array list.
            googleMap.addMarker(
                MarkerOptions().position(map.coordinateLocation)
                    .icon(BitmapDescriptorFactory.fromResource(markerIcon))
            )

            // below lin is use to zoom our camera on map.
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f))

        }

        // below line is use to move our camera to the specific location.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(eventList[position].coordinateLocation))
    }
}
