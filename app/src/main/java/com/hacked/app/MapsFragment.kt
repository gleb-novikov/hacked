package com.hacked.app

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hacked.app.geodesy.Geodesy
import es.dmoral.toasty.Toasty

class MapsFragment : Fragment() {
    private lateinit var map: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var navController: NavController
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                checkLocationPermission()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                checkLocationPermission()
            } else -> {
                // No location access granted.
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        checkLocationPermission()
        map.setOnMyLocationChangeListener {
            latitude = it.latitude
            longitude = it.longitude
            Log.d("LOCATION", "${it.latitude} ${it.longitude}")
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(16f))
        }
        map.setOnPoiClickListener {
            Log.d("POI_DATA", "${it.name} (${it.placeId}): ${it.latLng.latitude}, ${it.latLng.longitude}")
            if (Geodesy.getDistance(latitude, longitude, it.latLng.latitude, it.latLng.longitude) < 150)
                navController.navigate(R.id.action_mapsFragment_to_hackFragment)
            else
                Toasty.info(requireContext(), "Подойдите ближе", Toast.LENGTH_SHORT, true).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        navController = view.findNavController()
        val buttonProfile: ImageButton = view.findViewById(R.id.button_profile)
        buttonProfile.setOnClickListener {
            navController.navigate(R.id.action_mapsFragment_to_profileFragment)
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationPermission() {
        val findLocation = requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (findLocation == PackageManager.PERMISSION_GRANTED &&
            coarseLocation == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        } else {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }
}