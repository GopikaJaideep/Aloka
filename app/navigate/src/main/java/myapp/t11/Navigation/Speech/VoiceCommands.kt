package myapp.t11.Navigation.Speech

import java.util.*

enum class VoiceCommandsEN {
    navigation,
    compass,
    location,
    time,
    date,
    help,
    default
}
enum class NavigationVoiceCommands {
    StreetName,
    ConfirmStreet,
    TransitType
}

enum class TransitTypes {
    walking,
    driving,
    bicycling,
    transit
}
