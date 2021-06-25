package com.lab06.presentation.location

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lab06.R

class LocationFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
            context
        )
        val lat = sharedPreferences.getString("lat", "-34.0")!!.toDouble()
        val lng = sharedPreferences.getString("lng", "151.0")!!.toDouble()
        createMarker(lat, lng, googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun createMarker(lat: Double, lng: Double, map: GoogleMap) {
        val coordinates = LatLng(lat, lng)
        val marker = MarkerOptions().position(coordinates).title("Apartment")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )
    }

}