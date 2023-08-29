package real.erstate.realestateagency_1.ui.fragment.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.model.ApartmentA
import real.erstate.realestateagency_1.databinding.ItemTaskTwoBinding
import real.erstate.realestateagency_1.ui.util.loadImage

class AdapterAll(
    private val onClick: (id:String) -> Unit,
    private val fav: (asd:Boolean,id:String) -> Unit,
    private val onLong: (id:String) -> Unit,
    ) : ListAdapter<ApartmentA, AdapterAll.ViewHolder>(DiffCallback()) {
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
                if (item.best == true) {
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
                } else {

                }
            }
            itemView.setOnClickListener {
                onClick(binding.tvId.text.toString())
            }
            binding.heart.setOnClickListener {
                isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    fav(isButtonClicked,binding.tvId.text.toString())
                    binding.heart.setImageResource(R.drawable.heart_red)
                } else {
                    binding.heart.setImageResource(R.drawable.heart)
                }

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
                    fav(isButtonClicked,binding.tvId.text.toString())
                    binding.heart.setImageResource(R.drawable.heart_red)
                } else {
                    binding.heart.setImageResource(R.drawable.heart)
                }
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
