package real.erstate.realestateagency_1.ui.fragment.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ItemRoomBinding


class AdapterRoom(private val onClick: (String) -> Unit):RecyclerView.Adapter<AdapterRoom.ViewHolder>(){

    private var isButtonClicked = false
    private val list = arrayListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun load(task: List<String>){
        list.clear()
        list.addAll(task)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: String) {
           binding.tvb.text = task
            binding.llIvOne.setOnClickListener {
                isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    binding.ibm.setImageResource(R.drawable.card_bg)
                    binding.llIvOne.setBackgroundResource(R.drawable.bg_lyue)
                    val defaultColor = ContextCompat.getColor(binding.llIvOne.context, R.color.card)
                    binding.tvb.setTextColor(defaultColor)
                } else {
                    binding.ibm.setImageResource(R.drawable.room_black)
                    binding.llIvOne.setBackgroundResource(R.drawable.bg_black_tv_line)
                    val defaultColor = ContextCompat.getColor(binding.llIvOne.context, R.color.black)
                    binding.tvb.setTextColor(defaultColor)
                }
                onClick(binding.tvb.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return  list.size   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.onBind(list[position])
    }

}