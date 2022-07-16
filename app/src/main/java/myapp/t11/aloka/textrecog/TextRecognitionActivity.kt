package myapp.t11.aloka.textrecog

import androidx.core.content.ContextCompat
import myapp.t11.aloka.BaseLensActivity

class TextRecognitionActivity : BaseLensActivity() {

    override val imageAnalyzer = TextAnalyzer(this,this)

    override fun startScanner() {
        startTextRecognition()
    }

    private fun startTextRecognition() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }
}

