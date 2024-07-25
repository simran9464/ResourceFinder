package com.example.disasterproject

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.health.connect.datatypes.ExerciseRoute.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.GoogleSourceStampsResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapActivity : AppCompatActivity() ,OnMapReadyCallback{
    private var mGoogleMap : GoogleMap? = null
    private lateinit var currentLoc:Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var autocompleteSupportFragment: AutocompleteSupportFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        Places.initialize(applicationContext,getString(R.string.google_map_api_key))
        autocompleteSupportFragment=supportFragmentManager.findFragmentById(R.id.searchMapFr) as AutocompleteSupportFragment
       autocompleteSupportFragment.setPlaceFields(listOf(Place.Field.ADDRESS,Place.Field.LAT_LNG))
       autocompleteSupportFragment.setOnPlaceSelectedListener(object:PlaceSelectionListener{
           override fun onError(p0: Status) {
                Toast.makeText(this@MapActivity,"Some error in the search",Toast.LENGTH_LONG).show()
           }

           @SuppressLint("SuspiciousIndentation")
           override fun onPlaceSelected(place: Place) {
            val latLng = place.latLng
               zoomOnMap(latLng)
           }

           
       })
       val mapFragment =supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    



    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
    }
    private fun zoomOnMap(latLng: LatLng){
//        how much zooom to
        val newLatlmgZoom = CameraUpdateFactory.newLatLngZoom(latLng,12f)
        mGoogleMap?.animateCamera(newLatlmgZoom)
    }
}