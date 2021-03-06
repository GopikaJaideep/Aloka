package myapp.t11.aloka.imagelabeler

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import myapp.t11.aloka.barcode.BarcodeAnalyzer

class ImageLabelAnalyzer(val context: Context, val si: BarcodeAnalyzer.SampleInterface) :
    ImageAnalysis.Analyzer {


    private val labeler = ImageLabeling.getClient(
        ImageLabelerOptions.Builder()
          .setConfidenceThreshold(0.5F)
            .build()
  )


  @SuppressLint("UnsafeOptInUsageError")
  override fun analyze(imageProxy: ImageProxy) {
      try {
            imageProxy.image?.let {
                val inputImage = InputImage.fromMediaImage(
                    it,
                    imageProxy.imageInfo.rotationDegrees
                )
                labeler.process(inputImage)
                   .addOnSuccessListener { labels ->
                      labels.forEach { label ->
                           si.getResult(" Object = ${label.text}\n Confidence = ${label.confidence}")
                          Log.d(
                              "LABEL", """
                            Format = ${label.text}
                            Confidence = ${label.confidence}
                         """.trimIndent()
                            )
                       }
                   }
                    .addOnFailureListener { ex ->
                        Log.e("LABEL", "Detection Failed", ex)
                    }
                  .addOnCompleteListener {
                       imageProxy.close()
                    }

            } ?: imageProxy.close()
        } catch (ex: Exception) {

        }
        Log.d("LABEL", "image analyzed")


    }

    interface SampleInterface {
        fun getResult(value: String)
    }

}