package myapp.t11.aloka

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import myapp.t11.aloka.barcode.BarcodeActivity
import myapp.t11.aloka.databinding.ActivityMain2Binding
import myapp.t11.aloka.facedetect.FaceDetectActivity
import myapp.t11.aloka.imagelabeler.ImageLabelingActivity
import myapp.t11.aloka.textrecog.TextRecognitionActivity



class Main : AppCompatActivity() {

    companion object {
        @JvmStatic
        val PHOTO_REQ_CODE = 234

        @JvmStatic
        val EXTRA_DATA = "data"
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter:RecyclerAdapter
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
       binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RecyclerAdapter()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }

        binding.btnTakeExtPhoto.setOnClickListener {
            Log.e("btnTakeExtPhoto", "onCreate: 123444", )
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                startActivityForResult(this, PHOTO_REQ_CODE)
            }
        }

        binding.btnCameraActivity.setOnClickListener {
            Log.e("Camera", "onCreate: 123444", )
            startActivity(Intent(this, CameraActivity::class.java))
        }

       binding.btnBarcodeActivity.setOnClickListener {
           Log.e("btnBarcodeActivity", "onCreate: ", )
            startActivity(Intent(this, BarcodeActivity::class.java))
        }

        binding.btnFaceDetectActivity.setOnClickListener {
            Log.e("btnFaceDetectActivity", "onCreate: 123444", )
            startActivity(Intent(this, FaceDetectActivity::class.java))
        }
        binding.btnLabelerActivity.setOnClickListener {
            Log.e("btnLabelerActivity", "onCreate: 123444", )
            startActivity(Intent(this, ImageLabelingActivity::class.java))
        }

        binding.btnTextRecogActivity.setOnClickListener {
            startActivity(Intent(this, TextRecognitionActivity::class.java))
        }
      binding.btnMap.setOnClickListener{
          val intent=Intent()
          intent.setClassName(packageName,"myapp.t11.aloka.navigate.Activities.NaviMain.kt")
          startActivity(intent)
      }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

            if (requestCode == PHOTO_REQ_CODE) {
                val bitmap = data?.extras?.get(EXTRA_DATA) as Bitmap

                binding.imgThumb.setImageBitmap(bitmap)
                return
            }

            super.onActivityResult(requestCode, resultCode, data)

        }
    }