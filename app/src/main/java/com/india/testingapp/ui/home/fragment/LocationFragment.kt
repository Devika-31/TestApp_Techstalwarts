package com.india.testingapp.ui.home.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.india.testingapp.databinding.LocationFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String
import java.util.Locale


@AndroidEntryPoint
class LocationFragment : Fragment() {
    private lateinit var binding: LocationFragmentBinding
    private var client: FusedLocationProviderClient? = null
    var lat:Double?=null
    var lon:Double?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(layoutInflater)
        client = LocationServices
            .getFusedLocationProviderClient(
                requireActivity()
            );
        binding.buttonLaunch.setOnClickListener {
            if(lat != null && lon!= null){
                val uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat?.toFloat(), lon?.toFloat())
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }
        getUserLocation()
        return binding.root
    }

    private fun getUserLocation() {
        if (checkForLocationPermission()) {
            val locationManager = requireActivity()
                .getSystemService(
                    Context.LOCATION_SERVICE
                ) as LocationManager
            if (locationManager.isProviderEnabled(
                    LocationManager.GPS_PROVIDER
                )
                || locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER
                )
            ) {

                client?.lastLocation?.addOnCompleteListener {
                    if(it.isComplete){
                        val location = it.result
                        if(location!=null){
                            lat = location.latitude
                            lon = location.longitude
                            binding.textLat.text = "Lat: $lat"
                            binding.textLon.text = "Lon: $lon"
                        }else{
                            val locationRequest: LocationRequest = LocationRequest()
                                .setPriority(
                                    LocationRequest.PRIORITY_HIGH_ACCURACY
                                )
                                .setInterval(10000)
                                .setFastestInterval(
                                    1000
                                )
                                .setNumUpdates(1)

                            val locationCallback = object :LocationCallback(){
                                override fun onLocationResult(p0: LocationResult?) {
                                    val loc = p0?.locations?.first()
                                    lat = loc?.latitude
                                    lon = loc?.longitude
                                    binding.textLat.text = "Lat: $lat"
                                    binding.textLon.text = "Lon: $lon"
                                }
                            }


                            client?.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper());
                        }
                    }
                }
            }else{
                startActivity(
                    Intent(
                        Settings
                            .ACTION_LOCATION_SOURCE_SETTINGS)
                        .setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )
        }
    }

    private fun checkForLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission
                .ACCESS_FINE_LOCATION
        )
                == PackageManager
            .PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission
                .ACCESS_COARSE_LOCATION
        )
                == PackageManager
            .PERMISSION_GRANTED)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<kotlin.String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        // Check condition
        if (requestCode == 100 && grantResults.isNotEmpty()
            && (grantResults[0] + grantResults[1]
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            getUserLocation()
        } else {
            Toast
                .makeText(
                    activity,
                    "Permission denied",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    }
}