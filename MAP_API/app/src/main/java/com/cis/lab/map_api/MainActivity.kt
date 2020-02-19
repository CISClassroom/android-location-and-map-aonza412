package com.cis.lab.map_api

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        button.setOnClickListener{
            val i = Intent(this,MapsActivity::class.java)
            if (checkPermissions()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    var lat = location?.latitude.toString()
                    var lon = location?.longitude.toString()
                    i.putExtra("lat", lat.toString())
                    i.putExtra("lon", lon.toString())
                    startActivity(i)
                }
            }else {
                requestPermissions()
            }

        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
//            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location:Location? = task.result
                    if (location == null) {
//                        requestNewLocationData()
                        Log.i("Location","Null Location")
                    } else {
                        var lat= location.latitude.toString()
                        var lon = location.longitude.toString()
                        textView.text=("Location is "+lat+" : "+lon)
                    }
                }
//            } else {
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
    }
//
//    private fun isLocationEnabled(): Boolean {
//        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }

//    @SuppressLint("MissingPermission")
//    private fun requestNewLocationData() {
//        var mLocationRequest = LocationRequest()
//        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        mLocationRequest.interval = 0
//        mLocationRequest.fastestInterval = 0
//        mLocationRequest.numUpdates = 1
//
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        mFusedLocationClient!!.requestLocationUpdates(
//            mLocationRequest, mLocationCallback, Looper.myLooper()
//        )
//    }

//    private val mLocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            var mLastLocation: Location = locationResult.lastLocation
//            latitude_text.text = mLastLocation.latitude.toString()
//            longitude_text.text = mLastLocation.longitude.toString()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == PERMISSION_ID) {
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                getLastLocation()
//            }
//        }
//    }
}