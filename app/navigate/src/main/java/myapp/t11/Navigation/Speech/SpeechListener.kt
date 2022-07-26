package myapp.t11.Navigation.Speech

import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.annotation.RequiresApi
import myapp.t11.Navigation.Activities.NaviMain.Companion.speechDetectionCount
import myapp.t11.Navigation.Activities.NaviMain.Companion.speechResult
@RequiresApi(Build.VERSION_CODES.FROYO)

class SpeechListener<E: Enum<E>>(private var audioManager: AudioManager, private val notificationsVolume: Int, private val systemVolume: Int,  private val musicVolume: Int, private val voiceCommandManager: VoiceCommandManager, private var commandType: E) :RecognitionListener {



    override fun onBeginningOfSpeech() {
        Log.wtf("1", "onBeginningOfSpeech")
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
    }


    override fun onBufferReceived(buffer: ByteArray) {
        Log.wtf("1", "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.wtf("1", "onEndOfSpeech")
        if(speechDetectionCount<2) {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
        }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_NO_MATCH) { }
        else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {}

        Log.wtf("1", "onError" + error)
        if (speechDetectionCount < 2) {
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0)
            voiceCommandManager.recognizeSpeech(commandType)
        }
        else if (speechDetectionCount >= 2) {
//            activity.voiceCommandsButton.setImageResource(R.drawable.mic_not_clicked)
            audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, systemVolume, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationsVolume, 0)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume, 0)
        }
    }

    override fun onEvent(eventType: Int, params: Bundle) {
        Log.wtf("1", "onEvent")
    }

    override fun onPartialResults(partialResults: Bundle) {
        Log.wtf("1", "onPartialResults")
    }

    override fun onReadyForSpeech(params: Bundle) {
        Log.wtf("1", "onReadyForSpeech")
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onResults(results: Bundle) {

        val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        speechResult = data!![0]
        speechDetectionCount=0;
//        activity.voiceCommandsButton.setImageResource(R.drawable.mic_not_clicked)

        // Let the voice command manager handle the command
        Log.wtf("voiceresult", speechResult + " " + commandType)
        voiceCommandManager.handleVoiceCommands(speechResult.toLowerCase(), commandType)

        Log.wtf("volume", systemVolume.toString() + " " + notificationsVolume)
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, systemVolume, 0)
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationsVolume,0 );
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, musicVolume,0 );
        Log.wtf("1", "onResults")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.wtf("1", "onRmsChanged")
    }

}