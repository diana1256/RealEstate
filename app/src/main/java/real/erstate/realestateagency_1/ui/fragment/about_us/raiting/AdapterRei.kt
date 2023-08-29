package real.erstate.realestateagency_1.ui.fragment.about_us.raiting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.RewList
import real.erstate.realestateagency_1.data.model.RewSour
import real.erstate.realestateagency_1.databinding.ItemReitingBinding

class AdapterRei(private val rewList: Resource<RewList>) : ListAdapter<RewSour,
        AdapterRei.ViewHolder>(DiffUtilNoteItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReitingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.onBind(rewList.data?.results!![position])
    }

    inner class ViewHolder(private val binding: ItemReitingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: RewSour) {
            binding.desc.text = note.review_text
            binding.tvName.text = note.fullname
            binding.ivStar.rating = note.stars.toFloat()
        }
    }

    override fun getItemCount(): Int = rewList.data?.results?.size ?: 0

    private class DiffUtilNoteItemCallback : DiffUtil.ItemCallback<RewSour>() {
        override fun areItemsTheSame(oldItem: RewSour, newItem: RewSour): Boolean {
            return oldItem.stars == newItem.stars
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RewSour, newItem: RewSour): Boolean {
            return oldItem == newItem
        }
    }
}