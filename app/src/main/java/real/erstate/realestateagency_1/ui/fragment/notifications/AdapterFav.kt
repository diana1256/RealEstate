package real.erstate.realestateagency_1.ui.fragment.notifications

import android.content.Context
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import real.erstate.realestateagency_1.data.entity.Favorite
import real.erstate.realestateagency_1.databinding.ItemFavBinding
import real.erstate.realestateagency_1.data.room.FavDB
import real.erstate.realestateagency_1.ui.util.loadImage

class AdapterFav(private val context: Context, private val favItemList: MutableList<Favorite>,private val onClick:(Favorite,idf:String) -> Unit) :
    ListAdapter<Favorite, AdapterFav.ViewHolder>(DiffCallback()) {

    private lateinit var favDB: FavDB
    private lateinit var refLike: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        favDB = FavDB(context)
        return ViewHolder(ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = favItemList[position].tvTitle
        Log.i("okiju", "onBindViewHolder:${favItemList[position].image}")
        holder.photo.loadImage(favItemList[position].image)
        holder.dil.text = favItemList[position].tvDil
        holder.km.text = favItemList[position].tvKm
        val pri = favItemList[position].tvSan
        val formattedNumber = pri.replace(".0+$".toRegex(), "")
        holder.location.text = favItemList[position].tvLocation
        holder.san.text = formattedNumber
        holder.room.text = favItemList[position].tvRoom
        holder.status.text = favItemList[position].status
    }

    override fun getItemCount(): Int {
        return favItemList.size
    }

    inner class ViewHolder(binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root) {

        var status:TextView = binding.tvId
        var photo: ImageView = binding.ivPhoto
        var title: TextView = binding.tvTitle
        var heart: ImageView = binding.heart
        var dil: TextView = binding.tvDil
        var room: TextView = binding.tvRoom
        var km: TextView = binding.tvKm
        var san: TextView = binding.tvSan
        var location: TextView = binding.tvLocation
        init {
            refLike = FirebaseDatabase.getInstance().reference.child("likes")
            heart.setOnClickListener {
                val position = adapterPosition
                val favItem = favItemList[position]
                val upvotesRefLike = refLike.child(favItem.id)
                favDB.removeFav(favItem.id)
                removeItem(position)
                upvotesRefLike.runTransaction(object : Transaction.Handler {
                    override fun doTransaction(mutableData: MutableData): Transaction.Result {
                        try {
                            val currentValue = mutableData.getValue(Int::class.java)
                            if (currentValue == null) {
                                mutableData.value = 1
                            } else {
                                mutableData.value = currentValue - 1
                            }
                        } catch (e: Exception) {
                            throw e
                        }
                        return Transaction.success(mutableData)
                    }

                    override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot?) {
                        println("Transaction completed")
                    }
                })
            }
        }
    }

    private fun removeItem(position: Int) {
        favItemList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favItemList.size)
    }

    class DiffCallback : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }
}