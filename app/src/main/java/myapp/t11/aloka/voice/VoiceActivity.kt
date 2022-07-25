package myapp.t11.aloka.voice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alan.alansdk.AlanConfig
import myapp.t11.aloka.databinding.ActivityVoiceBinding


class VoiceActivity : AppCompatActivity() {


    private lateinit var binding: ActivityVoiceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val config = AlanConfig.builder().setProjectId("541ac035620feac87c3516659e585ee12e956eca572e1d8b807a3e2338fdd0dc/stage").build()
        binding.alanButton.initWithConfig(config)
    }
}