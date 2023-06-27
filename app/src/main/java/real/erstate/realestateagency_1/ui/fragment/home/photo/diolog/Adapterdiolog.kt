package real.erstate.realestateagency_1.ui.fragment.home.photo.diolog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.databinding.ItemDiologBinding


class Adapterdiolog : ListAdapter<giolo, Adapterdiolog.ViewHolder>(DiffUtilNoteItemCallback()){

    inner class ViewHolder(private val binding : ItemDiologBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(task: giolo) {
         binding.iv.setImageResource(task.iv)
         binding.ivItem.setImageResource(task.vi_item)
         binding.tvIcon.setBackgroundResource(task.icon)
            binding.tvTitle.text = task.title
            binding.tvDecs.text = task.desc
            binding.iv.setImageResource(task.iv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemDiologBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    private class DiffUtilNoteItemCallback : DiffUtil.ItemCallback<giolo>() {
        override fun areItemsTheSame(oldItem: giolo, newItem: giolo): Boolean {
            return oldItem.iv == newItem.iv
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: giolo, newItem: giolo): Boolean {
            return oldItem == newItem
        }
    }
}