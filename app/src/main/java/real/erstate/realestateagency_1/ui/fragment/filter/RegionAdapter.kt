package real.erstate.realestateagency_1.ui.fragment.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ItemTvFilterBinding
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.DataReonse
import real.erstate.realestateagency_1.data.model.Image
import real.erstate.realestateagency_1.data.model.Region


class RegionAdapter(private val resource: Resource<DataReonse>, private val onClick: (Region) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Image, RegionAdapter.ViewHolder>(DiffCallback()) {

    private var isButtonClicked = false

    inner class ViewHolder(private val binding: ItemTvFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Region) {
             binding.tvThree.text = task.name
            binding.tvTh.text = task.id.toString()
            binding.llTvThree.setOnClickListener {
                isButtonClicked = !isButtonClicked
                if (isButtonClicked) {
                    binding.llTvThree.setBackgroundResource(R.drawable.bg_lyue)
                    val defaultColor = ContextCompat.getColor(binding.tvTh.context, R.color.card)
                    binding.tvThree.setTextColor(defaultColor)
                } else {
                    binding.llTvThree.setBackgroundResource(R.drawable.bg_black_tv_line)
                    val defaultColor = ContextCompat.getColor(binding.tvTh.context, R.color.black)
                    binding.tvThree.setTextColor(defaultColor)
                }
                onClick(task)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTvFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(resource.data?.results?.get(position)!!)
    }

       override fun getItemCount(): Int = resource.data?.results?.size ?: 0

    private class DiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}