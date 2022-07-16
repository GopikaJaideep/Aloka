package myapp.t11.aloka.barcode
import androidx.core.content.ContextCompat
import myapp.t11.aloka.BaseLensActivity

class BarcodeActivity() : BaseLensActivity() {

    override val imageAnalyzer = BarcodeAnalyzer(this,this)

    override fun startScanner() {
        scanBarcode()
    }
    private fun scanBarcode() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer

        )

    }
}
