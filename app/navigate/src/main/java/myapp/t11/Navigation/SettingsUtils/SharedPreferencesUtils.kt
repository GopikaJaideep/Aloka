package myapp.t11.Navigation.SettingsUtils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi

class SharedPreferencesUtils (private var context: Context){
    val settingsSharedPreferences = "settings"

    fun getStoredString(key: String): String? {
        val sp: SharedPreferences = context.getSharedPreferences(settingsSharedPreferences, 0)
        return sp.getString(key, "Default")
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun writeStoredString(key: String, value: String?) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(settingsSharedPreferences, 0).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStoredBool(key: String): Boolean? {
        val sp: SharedPreferences = context.getSharedPreferences(settingsSharedPreferences, 0)
        return sp.getBoolean(key, true)
    }

    @RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun writeStoredBool(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = context.getSharedPreferences(settingsSharedPreferences, 0).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }
}
