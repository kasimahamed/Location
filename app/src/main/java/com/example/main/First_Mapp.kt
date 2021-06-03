package com.example.main.Map_Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.main.Views.Name_Discription_view
import com.example.main.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

open class First_Mapp : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST = 1


    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
            map?.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                map?.isMyLocationEnabled = true
            } else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapp)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getLocationAccess()

        map?.setOnMapLongClickListener { latlng ->
            val markedPlaces = LatLng(latlng.latitude, latlng.longitude)
            map?.addMarker(MarkerOptions().position(markedPlaces))

            val lat: Double = latlng.latitude
            val lng: Double = latlng.longitude

            if (lat != null) {
                Toast.makeText(this, "Latitude Is :" + lat, Toast.LENGTH_SHORT).show()
            }
            if (lng != null) {
                Toast.makeText(this, "Longitude Is :" + lng, Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, Name_Discription_view::class.java)
            intent.putExtra("Lat", lat)
            intent.putExtra("Lng", lng)
            startActivity(intent)
            map?.clear()
        }
    }
}