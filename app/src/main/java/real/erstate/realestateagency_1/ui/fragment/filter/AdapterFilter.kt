package real.erstate.realestateagency_1.ui.fragment.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.RealEstate
import real.erstate.realestateagency_1.databinding.ItemFilBinding


class AdapterFilter() :
   RecyclerView.Adapter<AdapterFilter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun load(tes:List<RealEstate>){
        list.clear()
        list.addAll(tes)
        notifyDataSetChanged()
    }
    private var isButtonClicked = false
    private val list = arrayListOf<RealEstate>()

    inner class ViewHolder(private val binding: ItemFilBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: RealEstate) {
            binding.tvTitle.text = task.tvDil
            binding.ivIcon.setImageResource(task.image)
            binding.cardIv.setOnClickListener {
                    isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    binding.cardIv.setCardBackgroundColor(
                        ContextCompat.getColor(
                            binding.cardIv.context,
                            R.color.blue
                        )
                    )
                } else {
                    val defaultColor = ContextCompat.getColor(binding.cardIv.context, R.color.card)
                    binding.cardIv.setBackgroundColor(defaultColor)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

}