package real.erstate.realestateagency_1.ui.fragment.notifications

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.model.Response
import real.erstate.realestateagency_1.data.model.ResultFav
import real.erstate.realestateagency_1.databinding.ItemFavBinding
import real.erstate.realestateagency_1.ui.util.loadImage

class AdapterFav(private val fv: (asd:String) -> Unit,private val apartment: Response,private val onClick: (asd:String) -> Unit,private val fav: (nhj:Boolean) -> Unit): RecyclerView.Adapter<AdapterFav.HolderView>(){
    var img = ""
    var isButtonClicked = false
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
         return  HolderView(ItemFavBinding.inflate(LayoutInflater.from(parent.context),parent,false))
     }

     override fun getItemCount(): Int = apartment.results.size

     override fun onBindViewHolder(holder: HolderView, position: Int) {
         holder.onBind(apartment.results[position])
     }
    inner class HolderView(private val binding: ItemFavBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(item: ResultFav) {
            with(binding) {
                tvTitle.text = item.apartment.title
                tvCyrrenty.text = item.apartment.currency.symbol
                val pri = item.apartment.price
                val formattedNumber = pri.replace(".0+$".toRegex(), "")
                tvSan.text = formattedNumber
                tvKm.text = item.apartment.square
                tvRoom.text = item.apartment.room_count
                tvId.text = item.apartment.id
                tvIdFav.text = item.id.toString()
                tvDil.text = item.apartment.type.title
                val apartmentImages = item.apartment.apartment_images
                if (apartmentImages.isNotEmpty()) {
                    val firstImage = apartmentImages[0]
                    img = firstImage.image
                    ivPhoto.loadImage(img)
                    Log.i("ololoyu", "Bind:$img")
                }
                binding.tvLocation.text = item.apartment.address
                heart.setOnClickListener {
                    fav(isButtonClicked)
                    isButtonClicked = !isButtonClicked
                    if (isButtonClicked) {
                        heart.setImageResource(R.drawable.heart)
                        fav(isButtonClicked)
                    } else {
                        heart.setImageResource(R.drawable.heart_red)
                        fav(isButtonClicked)
                    }
                }
                fv(binding.tvIdFav.text.toString())
                itemView.setOnClickListener {
                    onClick(binding.tvId.text.toString())
                }
            }
        }
    }
}