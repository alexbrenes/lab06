package com.lab06.presentation.setLocation

import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.lab06.R
import java.util.jar.Manifest

class SetLocationFragment : Fragment() {

    private lateinit var client: FusedLocationProviderClient

    private val callback = OnMapReadyCallback { googleMap ->
        getCurrentLocation(googleMap)
        googleMap.setOnMapClickListener { it ->
            googleMap.clear()
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString("latNew", it.latitude.toString())
            editor.putString("lngNew", it.longitude.toString())
            editor.commit()
            createMarker(it.latitude, it.longitude, googleMap)
            Toast.makeText(
                context,
                "Coordenadas fijadas en: (" + it.latitude + ", " + it.longitude + ")",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = LocationServices.getFusedLocationProviderClient(requireActivity())
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }

    private fun getCurrentLocation(googleMap: GoogleMap) {
        Log.d("getCurrentLocation", "getCurrentLocation")
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("getCurrentLocation", "PERMISSION GRANTED")
            val task = client.lastLocation
            task.addOnSuccessListener(object : OnSuccessListener<Location> {
                override fun onSuccess(location: Location) {
                    if (location != null) {
                        val latlng = LatLng(location.latitude, location.longitude)
                        val options = MarkerOptions().position(latlng).title("Current location")
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10f))
                        googleMap.addMarker(options)
                        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                        val editor = sharedPreferences.edit()
                        editor.putString("latNew", latlng.latitude.toString())
                        editor.putString("lngNew", latlng.longitude.toString())
                        editor.commit()
                    }
                }
            })
        }else{
            Log.d("getCurrentLocation", "PERMISSION DENIED")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_set_location, container, false)
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