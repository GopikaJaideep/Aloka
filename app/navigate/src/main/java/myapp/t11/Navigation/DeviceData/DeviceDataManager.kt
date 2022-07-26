package myapp.t11.Navigation.DeviceData

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import myapp.t11.Navigation.SettingsUtils.PreferredLanguage
import myapp.t11.Navigation.SettingsUtils.Settings
import myapp.t11.Navigation.SettingsUtils.Utils
import myapp.t11.Navigation.Speech.SpeechSynthesizer
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.FROYO)
class DeviceDataManager(private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    var accelerometer: Accelerometer = Accelerometer(context)
    lateinit var currentLocation: CurrentLocation

    init {
        if(checkLocationPermissions() && checkInternetPermission())
            currentLocation = CurrentLocation(context)
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun speakCompassDirection()
    {
        SpeechSynthesizer(context, accelerometer.getCompassDirection())
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun speakCurrentLocation() = GlobalScope.launch()
    {
        SpeechSynthesizer(context, currentLocation.getAddress())
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun speakTime()
    {
        var pattern = "HH:mm"
        val calendarTime = Calendar.getInstance().time

        if(Settings.language == PreferredLanguage.English)
            pattern = "h:mm a"

        val timeFormat = SimpleDateFormat(pattern, Utils.languageToLocale(Settings.language))

        SpeechSynthesizer(context, timeFormat.format(calendarTime))
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun speakDate()
    {
        val calendarTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat(
            "EEEE, d MMMM, yyyy",
            Utils.languageToLocale(Settings.language)
        )
        SpeechSynthesizer(context, dateFormat.format(calendarTime))
    }

    fun checkLocationPermissions() : Boolean
    {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )  == PackageManager.PERMISSION_GRANTED
    }

    fun checkInternetPermission() : Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
    }
}