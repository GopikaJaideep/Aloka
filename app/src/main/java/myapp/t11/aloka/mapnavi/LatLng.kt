package myapp.t11.aloka.mapnavi

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LatLng(val lat: Double,val lng: Double) : Parcelable {
    override fun toString(): String {
        return "LatLng(lat=$lat, lng=$lng)"
    }
}