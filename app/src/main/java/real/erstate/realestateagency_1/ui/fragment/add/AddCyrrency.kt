package real.erstate.realestateagency_1.ui.fragment.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.databinding.ItemAddBinding
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Currency
import real.erstate.realestateagency_1.data.model.DataResponseList

class AddCyrrency(private val resource: Resource<DataResponseList>, private val onClick: (String) -> Unit) :
    ListAdapter<Currency, AddCyrrency.ViewHolder>(DiffCallback()) {


    inner class ViewHolder(private val binding: ItemAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(task: Currency) {
            binding.tv.text = task.symbol
            binding.tvId.text = task.id.toString()
            itemView.setOnClickListener {
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
        holder.onBind(resource.data!!.results[position])
    }

    override fun getItemCount(): Int = resource.data?.results?.size ?: 0

    private class DiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }
}
