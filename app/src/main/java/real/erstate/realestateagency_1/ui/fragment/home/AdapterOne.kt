package real.erstate.realestateagency_1.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ItemTaskBinding
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.ApartmentA
import real.erstate.realestateagency_1.data.room.FavDB
import real.erstate.realestateagency_1.databinding.ItemTaskTwoBinding
import real.erstate.realestateagency_1.ui.util.loadImage

class AdapterOne(
    private val onClick: (idwe:String) -> Unit,
    private val fav: (er: Boolean,id:String) -> Unit,
    private val onLong: (idwe:String) -> Unit
    ) : ListAdapter<ApartmentA, AdapterOne.ViewHolder>(DiffCallback()) {
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var isButtonClicked = false



    var img = ""

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApartmentA) {
            with(binding) {
                if (item.best == true) {
                    tvTit.text = item.title
                    tvCyr.text = item.currency.symbol
                    val pri = item.price
                    val formattedNumber = pri.replace(".0+$".toRegex(), "")
                    tvSn.text = formattedNumber
                    tvMd.text = item.square
                    tvRo.text = item.room_count
                    tvId.text = item.id
                     tvD.text = item.type.title
                    val apartmentImages = item.apartment_images
                    if (apartmentImages.isNotEmpty()) {
                        val firstImage = apartmentImages[0]
                        img = firstImage.image
                        ivPho.loadImage(img)
                        Log.i("ololoyu", "Bind:$img")
                    }
                    binding.tvLocat.text = item.address
                } else {

                }
            }
            itemView.setOnClickListener {
                onClick(binding.tvId.text.toString())
            }

            itemView.setOnLongClickListener {
                onLong(binding.tvId.text.toString())
                return@setOnLongClickListener true
            }

            binding.heat.setOnClickListener {
                isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    fav(isButtonClicked,binding.tvId.text.toString())
                    binding.heat.setImageResource(R.drawable.heart_red)
                } else {
                    binding.heat.setImageResource(R.drawable.heart)
                }
            }
        }
    }


private class DiffCallback : DiffUtil.ItemCallback<ApartmentA>() {
    override fun areItemsTheSame(oldItem: ApartmentA, newItem: ApartmentA): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ApartmentA, newItem: ApartmentA): Boolean {
        return oldItem == newItem
    }
}
}