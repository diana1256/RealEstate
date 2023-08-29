package real.erstate.realestateagency_1.ui.fragment.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.databinding.ItemAddBinding
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.DataReonse
import real.erstate.realestateagency_1.data.model.Image
import real.erstate.realestateagency_1.data.model.Region


class AdapterAdd(private val resource: Resource<DataReonse>, private val onClick: (String) -> Unit) :
    ListAdapter<Image, AdapterAdd.ViewHolder>(DiffCallback()) {


    inner class ViewHolder(private val binding: ItemAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Region) {
            binding.tv.text = task.name
            binding.tvId.text = task.id.toString()
            binding.tv.setOnClickListener {
                onClick(binding.tvId.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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