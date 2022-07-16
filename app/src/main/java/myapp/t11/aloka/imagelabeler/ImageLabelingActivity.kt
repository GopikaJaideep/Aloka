package myapp.t11.aloka.imagelabeler

import androidx.core.content.ContextCompat
import myapp.t11.aloka.BaseLensActivity

class ImageLabelingActivity : BaseLensActivity() {
    override val imageAnalyzer = ImageLabelAnalyzer(this,this)

    override fun startScanner() {
        startImageLabeling()
    }

    private fun startImageLabeling() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }

}