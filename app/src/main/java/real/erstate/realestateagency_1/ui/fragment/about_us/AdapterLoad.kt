package real.erstate.realestateagency_1.ui.fragment.about_us

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.databinding.AboutLoadRvBinding

class AdapterLoad : ListAdapter<ModelUs,
        AdapterLoad.ViewHolder>(DiffUtilNoteItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AboutLoadRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ViewHolder(private val binding: AboutLoadRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: ModelUs) {
        }
    }

    private class DiffUtilNoteItemCallback : DiffUtil.ItemCallback<ModelUs>() {
        override fun areItemsTheSame(oldItem: ModelUs, newItem: ModelUs): Boolean {
            return oldItem.star == newItem.star
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ModelUs, newItem: ModelUs): Boolean {
            return oldItem == newItem
        }
    }
}