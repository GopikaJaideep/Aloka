package myapp.t11.aloka.navigate.SettingsUtils
import java.util.*

class Utils {
    companion object{
        inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
            return enumValues<T>().find { it.name == name }
        }

        fun languageToLocale(preferredLanguage: PreferredLanguage): Locale
        {
            var locale = Locale.ENGLISH
            when(preferredLanguage)
            {
                PreferredLanguage.English -> locale =  Locale.ENGLISH
            }
            return locale
        }
    }
}