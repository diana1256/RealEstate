@file:Suppress("UnusedEquals")

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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ItemTaskTwoBinding
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.ApartmentA
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.room.FavDB
import real.erstate.realestateagency_1.databinding.ItemTaskBinding
import real.erstate.realestateagency_1.ui.util.loadImage


@Suppress("UnusedEquals")
class AdapterRealEstate(
    private val onClick: (id:String) -> Unit,
    private val fav: (asd:Boolean,id:String) -> Unit,
    private val onLong: (id:String) -> Unit
    ) : ListAdapter<ApartmentA, AdapterRealEstate.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskTwoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    var img = ""
    var isButtonClicked = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTaskTwoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApartmentA) {
            with(binding) {
                    tvTitle.text = item.title
                    tvCyrrenty.text = item.currency.symbol
                    val pri = item.price
                    val formattedNumber = pri.replace(".0+$".toRegex(), "")
                    tvSan.text = formattedNumber
                    tvKm.text = item.square
                    tvRoom.text = item.room_count
                    tvId.text = item.id
                    tvDil.text = item.type.title
                    val apartmentImages = item.apartment_images
                    if (apartmentImages.isNotEmpty()) {
                        val firstImage = apartmentImages[0]
                        img = firstImage.image
                        ivPhoto.loadImage(img)
                        Log.i("ololoyu", "Bind:$img")
                    }
                    binding.tvLocation.text = item.address
            }
            itemView.setOnClickListener {
                onClick(binding.tvId.text.toString())
            }
            itemView.setOnLongClickListener {
                onLong(binding.tvId.text.toString())
                return@setOnLongClickListener true
            }

            binding.heart.setOnClickListener {
                isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    binding.heart.setImageResource(R.drawable.heart_red)
                    fav(isButtonClicked,binding.tvId.text.toString())
                } else {
                    binding.heart.setImageResource(R.drawable.heart)
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