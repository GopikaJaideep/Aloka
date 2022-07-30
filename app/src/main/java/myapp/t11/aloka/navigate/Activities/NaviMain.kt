package myapp.t11.aloka.navigate.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.SurfaceView
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import myapp.t11.aloka.navigate.Activities.SettingsActivity
import myapp.t11.aloka.R
import myapp.t11.aloka.navigate.DeviceData.CurrentLocation
import myapp.t11.aloka.navigate.DeviceData.DeviceDataManager
import myapp.t11.aloka.navigate.ObstacleDetection.ObstacleDetection
import myapp.t11.aloka.navigate.SettingsUtils.*
import myapp.t11.aloka.navigate.Speech.SpeechSynthesizer
import myapp.t11.aloka.navigate.Speech.VoiceCommandManager
import myapp.t11.aloka.navigate.Speech.VoiceCommandsEN
import myapp.t11.aloka.databinding.ActivityNavBinding
import myapp.t11.aloka.opencv.android.BaseLoaderCallback
import myapp.t11.aloka.opencv.android.OpenCVLoader


// Camera renderer set size

class NaviMain : AppCompatActivity() {
    lateinit var deviceDataManager: DeviceDataManager
    private lateinit var obstacleDetection: ObstacleDetection
    private lateinit var voiceCommandManager: VoiceCommandManager
    private lateinit var audioManager: AudioManager
    private val REQUEST_CODE = 101
    private val TAG = "ONIPOT DEBUG"
    private lateinit var binding: ActivityNavBinding

    private var baseLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    binding.javaCameraView.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressLint("InvalidWakeLockTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityNavBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
     val powerManager = getSystemService(POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyWakelockTag")

        wakeLock.acquire(10*60*1000L /*10 minutes*/)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestAllPermissions()
        }


        supportActionBar!!.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_nav)
        OpenCVLoader.initDebug()

        setupSettings()
        setupUI()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        SpeechSynthesizer(this, "")
        deviceDataManager = DeviceDataManager( this)

        if(checkMicrophonePermission())
            voiceCommandManager = VoiceCommandManager(audioManager, deviceDataManager,this)

        if(checkCameraPermission())
            initObstacleDetection()
    }

    private fun initObstacleDetection()
    {
        if(Settings.obstacleDetectionEnabled){
            obstacleDetection = ObstacleDetection(this, deviceDataManager.accelerometer)
            binding.javaCameraView.visibility = SurfaceView.VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                binding.javaCameraView.alpha = 1f
            }
            binding.javaCameraView.setCvCameraViewListener(obstacleDetection)
        }
        else{
            binding.javaCameraView.disableView()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                binding.javaCameraView.alpha = 0f
            }
        }
    }

    private fun setupSettings()
    {
        val languageStr = SharedPreferencesUtils(this).getStoredString("language")
        val obstacleDetectionWidthStr = SharedPreferencesUtils(this).getStoredString("obstacleDetectionWidth")
        val obstacleDetectionHeightStr = SharedPreferencesUtils(this).getStoredString("obstacleDetectionHeight")

        val language = Utils.enumValueOfOrNull<PreferredLanguage>(languageStr!!)
        if (language != null) {
            Settings.language = language
        }

        Settings.obstacleDetectionEnabled = SharedPreferencesUtils(this).getStoredBool("obstacleDetectionEnabled")!!

        val obstacleDetectionWidth = Utils.enumValueOfOrNull<DetectionWidth>(
            obstacleDetectionWidthStr!!
        )
        if (obstacleDetectionWidth != null) {
            Settings.detectionWidth = obstacleDetectionWidth
        }

        val obstacleDetectionHeight = Utils.enumValueOfOrNull<DetectionHeight>(
            obstacleDetectionHeightStr!!
        )
        if (obstacleDetectionHeight != null) {
            Settings.detectionHeight = obstacleDetectionHeight
        }
    }



    @RequiresApi(Build.VERSION_CODES.FROYO)
    private fun setupUI() {
        binding.voiceCommandsButton.setOnClickListener {
            speechDetectionCount =0
            voiceCommandManager.recognizeSpeech(VoiceCommandsEN.default)
            voiceCommandManager.notificationsVolume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION)
            voiceCommandManager.systemVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
            voiceCommandManager.musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }

        binding.locationButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                deviceDataManager.speakCurrentLocation()
            }
        }

        binding.compassButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                deviceDataManager.speakCompassDirection()
            }
        }

        binding.timeButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                deviceDataManager.speakTime()
            }
        }

        binding.dateButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                deviceDataManager.speakDate()
            }
        }

        binding.settingsButton.setOnClickListener {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestAllPermissions()
    {
        val isFineLocationDenied = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )  == PackageManager.PERMISSION_DENIED
        val isCourseLocationDenied = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )  == PackageManager.PERMISSION_DENIED
        val isRecordAudioDenied = checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED
        val isCameraDenied = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
        val isInternetDenied = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED

        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET
        )

        if (isFineLocationDenied || isCourseLocationDenied || isRecordAudioDenied || isCameraDenied || isInternetDenied) {
            requestPermissions(permissions, REQUEST_CODE)
        }
    }


    fun checkMicrophonePermission() : Boolean
    {
        return checkCallingOrSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    fun checkCameraPermission() : Boolean
    {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.wtf("onRequestPermissionsResult", grantResults.toString())

        voiceCommandManager = VoiceCommandManager(audioManager, deviceDataManager, this)
        deviceDataManager.currentLocation = CurrentLocation(this)
        initObstacleDetection()
    }


    override fun onPointerCaptureChanged(hasCapture: Boolean) {}

    override fun onDestroy() {
        super.onDestroy()
        if (binding.javaCameraView != null) {
            binding.javaCameraView!!.disableView()
        }

       //myLocation.stopLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        if (binding.javaCameraView != null) {
            binding.javaCameraView!!.disableView()
        }
     //myLocation.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        if (OpenCVLoader.initDebug()) {
            Log.d(TAG, "OpenCV is working correctly")
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS)
        } else {
            Log.d(TAG, "OpenCV is not working correctly")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback)
        }
 //  myLocation.startLocationUpdates()
    }


    companion object {
        var speechDetectionCount = 0;
        var speechResult = ""
        var lastChosenStreet = ""
    }
}

