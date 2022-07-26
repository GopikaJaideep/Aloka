package myapp.t11.Navigation.DeviceData

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*


@RequiresApi(Build.VERSION_CODES.FROYO)
class CurrentLocation(private var context: Context) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient;
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var lastLocation: Location
    private val geocoder:Geocoder

    private val INTERVAL = 1000L
    private val FASTEST_INTERVAL = 500L
    private val SMALLEST_DISPLACEMENT = 0f


    init
    {
        lastLocation = Location("")
        geocoder = Geocoder(context)
        locationUpdatePeriodically()
        startLocationUpdates()
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun updateLocation()
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            Log.wtf("location", "requiered location permissions")
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if(location!=null)
            {
                lastLocation = location
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    private fun locationUpdatePeriodically()
    {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationRequest = LocationRequest()
        locationRequest.interval = INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        locationRequest.smallestDisplacement = SMALLEST_DISPLACEMENT
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationCallback = object : LocationCallback() {

          @RequiresApi(Build.VERSION_CODES.FROYO)
          override fun onLocationResult(locationResult: LocationResult) {
              locationResult ?: return
                if (locationResult.locations.isNotEmpty()) {
                    lastLocation = locationResult.lastLocation!!
                    Log.wtf("locationchange", lastLocation.toString())
                   Toast.makeText(context, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        updateLocation()
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ) {
            Log.wtf("location", "requiered location permissions")
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun getAddress() : String
    {
        Log.wtf("getaddress", lastLocation.toString())
        var response = ""

        if(checkInternetConenction()) {
            val addresses = geocoder.getFromLocation( lastLocation.latitude,  lastLocation.longitude,   1  ) as ArrayList<Address?>
            if (addresses.size > 0) {
                val street = addresses[0]?.thoroughfare
                val streetNumber = addresses[0]?.subThoroughfare
                val city = addresses[0]?.locality

                response = street + " " + streetNumber + ", " + city
            }
        }
        else {
                response = "no internet connection, your latitude is: " + lastLocation.latitude.toString() + ", longitude: " + lastLocation.longitude.toString()
        }
        return response
    }

    private fun checkInternetConenction(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null
    }
}