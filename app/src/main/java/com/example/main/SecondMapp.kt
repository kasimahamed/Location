package com.example.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.main.dataclass.LocationDataClass
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class SecondMapp : AppCompatActivity(), OnMapReadyCallback {
    private var map: GoogleMap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_mapp)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.addValueEventListener(datafromdb)
    }
        val datafromdb = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val placedata: LocationDataClass? = snapshot.child("Datas").getValue(LocationDataClass::class.java)
                    val latitude: Double? = placedata?.latitude
                    val longitude: Double? = placedata?.longitude
                    if (latitude != null && longitude != null) {
                        val dataslatlng: LatLng = LatLng(latitude, longitude)
                        val markerOptions: MarkerOptions = MarkerOptions().position(dataslatlng)
                        map?.addMarker(markerOptions)
                        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(dataslatlng, 15f))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Can Not Take Data's From Database", Toast.LENGTH_SHORT).show()
            }
        }
    }
