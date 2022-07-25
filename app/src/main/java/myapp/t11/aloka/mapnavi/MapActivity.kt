package myapp.t11.aloka.mapnavi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import kotlin.math.*
object MapActivity {

    private const val TAG = "MapHelper"
    const val GOOGLE_MAP_PACKAGE_NAME = "com.google.android.apps.maps"

    const val AMAP_PACKAGE_NAME = "com.autonavi.minimap"
    object CoordinateType{

        const val GCJ02 = "gcj02"

        const val WGS84 = "wgs84"

        const val BD09LL = "bd09ll"

        const val BD09MC = "bd09mc"
    }

    object TripMode{

        const val DRIVING_MODE = 0

        const val TRANSIT_MODE = 1

        const val WALKING_MODE = 2

        const val RIDING_MODE = 3


        internal const val GOOGLE_DRIVING_MODE = "d"

        internal const val GOOGLE_TRANSIT_MODE = "l"

        internal const val GOOGLE_WALKING_MODE = "w"

        internal const val GOOGLE_RIDING_MODE = "b"

        //---------------------------------------------
    }

    private var DEFAULT_REFERER =  "AppKey"

    //---------------------------------------------
    object MapType{

        const val UNSPECIFIED_MAP_TYPE = 0

        const val GOOGLE_MAP_TYPE = 1

    }
    fun isInstalled(context: Context, packageName: String): Boolean {
        val manager = context.packageManager
        val installedPackages = manager.getInstalledPackages(0)
        for (info in installedPackages) {
            if (info.packageName == packageName) return true
        }
        return false
    }

    fun gotoMap(context: Context, toLatitude: Double, toLongitude: Double,mapType: Int = MapType.UNSPECIFIED_MAP_TYPE,isMarket: Boolean = false,isContainGoogle: Boolean = true): Boolean{
        return when (mapType){
            MapType.GOOGLE_MAP_TYPE -> gotoGoogleMap(context,toLatitude,toLongitude,isMarket = isMarket)
            else -> false
        }
    }

    fun gotoGoogleMap(context: Context, toLatitude: Double, toLongitude: Double,isMarket: Boolean = false,marketPackage: String? = null): Boolean{
        return gotoGoogleMapNavigation(context,toLatitude,toLongitude, TripMode.DRIVING_MODE,isMarket,marketPackage)
    }

    fun gotoGoogleMapNavigation(context: Context, toLatitude: Double, toLongitude: Double,mode: Int = TripMode.DRIVING_MODE,isMarket: Boolean = false,marketPackage: String? = null): Boolean{
        val tripMode = getGoogleTripMode(mode)
        val uri = "google.navigation:q=${toLatitude},${toLongitude}&mode=${tripMode}"
        return gotoUri(context,GOOGLE_MAP_PACKAGE_NAME,Uri.parse(uri),isMarket,marketPackage)
    }
    private fun getGoogleTripMode(mode: Int) = when(mode){
        TripMode.DRIVING_MODE -> TripMode.GOOGLE_DRIVING_MODE
        TripMode.TRANSIT_MODE -> TripMode.GOOGLE_TRANSIT_MODE
        TripMode.WALKING_MODE -> TripMode.GOOGLE_WALKING_MODE
        TripMode.RIDING_MODE -> TripMode.GOOGLE_RIDING_MODE
        else -> TripMode.GOOGLE_DRIVING_MODE
    }


    private fun gotoUri(context: Context,packageName: String,uri: Uri,isMarket: Boolean,marketPackage: String? = null): Boolean{
        try{
            Log.d(TAG,"Uri:$uri")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setPackage(packageName)
            if(intent.resolveActivity(context.packageManager) != null){
                context.startActivity(intent)
                return true
            }else{
                Log.d(TAG,"An App with Identifier '${packageName}' is not available.")
                if(isMarket){//是否跳转到应用市场
                    return gotoMarket(context,packageName,marketPackage)
                }
            }
        }catch (e: Exception){
            Log.w(TAG,e)
        }
        return false
    }

    private fun gotoMarket(context: Context,packageName: String,marketPackage: String? = null): Boolean{
        try{
            val marketUri = Uri.parse("market://details?id=${packageName}")
            val marketIntent = Intent(Intent.ACTION_VIEW,marketUri)
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if(!TextUtils.isEmpty(marketPackage)){
                marketIntent.setPackage(marketPackage)
            }
            context.startActivity(marketIntent)
            return true
        }catch (e: Exception){
            Log.w(TAG,e)
        }
        return false
    }


    private const val PI = 3.14159265358979323846

    private const val X_PI = PI * 3000.0 / 180.0


      fun bd09llToGCJ02(lat: Double,lng: Double): LatLng {
        var x = lng - 0.0065
        var y = lat - 0.006
        var z = sqrt(x * x + y * y) - 0.00002 * sin(y * X_PI)
        var theta = atan2(y, x) - 0.000003 * cos(x * X_PI)
        var ggLat = z * sin(theta)
        var ggLng = z * cos(theta)
        return LatLng(ggLat,ggLng)
    }

    fun gcj02ToBD09LL(lat: Double,lng: Double): LatLng {
        var z = sqrt(lng * lng + lat * lat) + 0.00002 * sin(lat * X_PI)
        var theta = atan2(lat, lng) + 0.000003 * cos(lng * X_PI)
        var bdLat = z * sin(theta) + 0.006
        var bdLng = z * cos(theta) + 0.0065
        return LatLng(bdLat,bdLng)
    }

    private fun transformLat(lat: Double,lng: Double): Double{
        var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * sqrt(abs(lng))
        ret += (20.0 * sin(6.0 * lng * PI) + 20.0 * sin(2.0 * lng * PI)) * 2.0 / 3.0
        ret += (20.0 * sin(lat * PI) + 40.0 * sin(lat / 3.0 * PI)) * 2.0 / 3.0
        ret += (160.0 * sin(lat / 12.0 * PI) + 320 * sin(lat * PI / 30.0)) * 2.0 / 3.0
        return ret
    }
    private fun transformLng(lat: Double,lng: Double): Double {
        var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * sqrt(abs(lng))
        ret += (20.0 * sin(6.0 * lng * PI) + 20.0 * sin(2.0 * lng * PI)) * 2.0 / 3.0
        ret += (20.0 * sin(lng * PI) + 40.0 * sin(lng / 3.0 * PI)) * 2.0 / 3.0
        ret += (150.0 * sin(lng / 12.0 * PI) + 300.0 * sin(lng / 30.0 * PI)) * 2.0 / 3.0
        return ret
    }
}

