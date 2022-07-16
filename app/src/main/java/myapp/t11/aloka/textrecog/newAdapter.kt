package myapp.t11.aloka.textrecog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import myapp.t11.aloka.R
import myapp.t11.aloka.databinding.CardLayoutBinding

class newAdapter(private val context:Context) : RecyclerView.Adapter<newAdapter.NewAdapterViewHolder>() {
    private var title = arrayOf(
        "QR/Barcode scanning",
        "Face detection",
        "Image labeling",
        "Text recognition"
    )
    private var desc =
        arrayOf(
            "Scan and process barcodes. Supports most standard 1D and 2D formats.",
            "Detect faces and facial landmarks.",
            "Identify objects, locations, activities, animal species, products, and more. Use a general-purpose base model to your use case with a custom TensorFlow Lite model.",
            "Recognize and extract text from images."
        )

    private var image = intArrayOf(
        R.drawable.barcode_scanning,
        R.drawable.face_detection,
        R.drawable.image_labeling,
        R.drawable.text_recognition
    )

    inner  class NewAdapterViewHolder(var binding: CardLayoutBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewAdapterViewHolder {
      return  NewAdapterViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: NewAdapterViewHolder, position: Int) {
        holder.binding.tvTitle.text=title[position]
    }

    override fun getItemCount(): Int {
        return 4
    }
}