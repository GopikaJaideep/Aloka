package myapp.t11.Navigation.Speech

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import myapp.t11.Navigation.Activities.NaviMain
import myapp.t11.Navigation.Activities.NaviMain.Companion.lastChosenStreet
import myapp.t11.Navigation.DeviceData.DeviceDataManager
import myapp.t11.Navigation.SettingsUtils.PreferredLanguage
import myapp.t11.Navigation.SettingsUtils.Settings
import myapp.t11.Navigation.SettingsUtils.Utils

@RequiresApi(Build.VERSION_CODES.FROYO)
class VoiceCommandManager(val audioManager: AudioManager, private val deviceDataManager: DeviceDataManager, private var context: Context) {

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    var notificationsVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
    var systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
    var musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun <E : Enum<E>> handleVoiceCommands(message: String, commandType: E)
    {
        Log.wtf("voicecommand", message)
        if(Settings.language == PreferredLanguage.English) {
            when (commandType) {
                VoiceCommandsEN.default -> handleMainVoiceCommands(message)
                NavigationVoiceCommands.StreetName -> {
                    lastChosenStreet = message
                    RespondingSpeechSynthesizer(context, "Your destination is: " + message + ", correct?", NavigationVoiceCommands.ConfirmStreet, ::recognizeSpeech)
                }
                NavigationVoiceCommands.ConfirmStreet -> {
                    if (message == "yes" || message == "correct")
                        startNavigation(lastChosenStreet)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun handleMainVoiceCommands(message: String)
    {
        if(Settings.language == PreferredLanguage.English)
        {
            val command = Utils.enumValueOfOrNull<VoiceCommandsEN>(message)
            when (command) {
                VoiceCommandsEN.navigation -> navigationCommand()
                VoiceCommandsEN.compass -> deviceDataManager.speakCompassDirection()
                VoiceCommandsEN.location -> deviceDataManager.speakCurrentLocation()
                VoiceCommandsEN.time -> deviceDataManager.speakTime()
                VoiceCommandsEN.date -> deviceDataManager.speakDate()
                VoiceCommandsEN.help -> speakAvailableCommands()
                else ->  SpeechSynthesizer(context,message + ", is not a command, say a word 'help' to get more info."
                )
            }
        }

    }

    fun navigationCommand()  {
        var message = ""
        if(Settings.language == PreferredLanguage.Polish)
            message = "Podaj miejsce docelowe"
        else if (Settings.language == PreferredLanguage.English)
            message = "What's your destination"

        RespondingSpeechSynthesizer(context, message, NavigationVoiceCommands.StreetName, ::recognizeSpeech)
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun speakAvailableCommands()
    {
        var commands = ""
        if (Settings.language == PreferredLanguage.English)
            commands = "Available commands: "+  listToString(VoiceCommandsEN.values())

        SpeechSynthesizer(context, commands)
    }

    fun <E : Enum<E>> listToString(values: Array<E>): String
    {
        var s = ""
        var index =0
        for(value in values)
        {
            if(index == values.size-2)
                break

            // Commas give pauses between words
            s+= value.name + ",,,,,,,, "
            index++
        }
        return s
    }

    fun startNavigation(address: String) = GlobalScope.launch()
    {
        if(address!="")
        {
            //Uri.parse("google.navigation:q="+street+",+Wroclaw+Poland&mode=transit");
            // travel modes: driving - d, walking - w, bicycling - b, transit - t

//            val gmmIntentUri = Uri.parse("google.navigation:q=" + address + ",+Wroclaw+Poland&mode=w");
            val gmmIntentUri = Uri.parse("google.navigation:q=" + address + "&mode=w");
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.addFlags(Intent.FLAG_FROM_BACKGROUND)
            mapIntent.setPackage("com.google.android.apps.maps")
            context.startActivity(mapIntent)

        }
    }

    fun <E : Enum<E>> recognizeSpeech(commandType: E){
        Log.wtf("voiceresult", commandType.name)
        NaviMain.speechDetectionCount++

        val speechListener = SpeechListener(audioManager, notificationsVolume, systemVolume, musicVolume, this, commandType)
        speechRecognizer.setRecognitionListener(speechListener)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        val locale = Utils.languageToLocale(Settings.language)
        Log.wtf("voicecommand", Settings.language.name + " " + locale + " " + locale.toString() + " " + commandType.name)

        if(commandType == NavigationVoiceCommands.StreetName){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toString());
        }
        else {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toString());
        }

        try{
            speechRecognizer.startListening(intent)
        }
        catch (e: Exception){
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}