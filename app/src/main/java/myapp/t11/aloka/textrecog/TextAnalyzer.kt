package myapp.t11.aloka.textrecog

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import myapp.t11.aloka.barcode.BarcodeAnalyzer


class TextAnalyzer(val context: Context, val si: BarcodeAnalyzer.SampleInterface) :
    ImageAnalysis.Analyzer {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    @SuppressLint("UnsafeExperimentalUsageError", "UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        Log.d("TEXT", "image analysed")

        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(
                it,
                imageProxy.imageInfo.rotationDegrees
            )

            recognizer.process(inputImage)
                .addOnSuccessListener { text ->
                    text.textBlocks.forEach { block ->

                        si.getResult("LINES = ${block.lines.joinToString("\n") { it.text}}")

                        Log.d(
                            "TEXT", """
                            LINES = ${block.lines.joinToString("\n") { it.text }}
                        """.trimIndent()
                        )
                    }
                }
                .addOnFailureListener { ex ->
                    Log.e("TEXT", "Detection failed", ex)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }

        } ?: imageProxy.close() // close if image not found either

    }

    interface SampleInterface {
        fun getResult(value: String)
    }
}