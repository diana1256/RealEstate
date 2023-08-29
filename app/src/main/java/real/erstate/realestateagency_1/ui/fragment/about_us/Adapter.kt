package real.erstate.realestateagency_1.ui.fragment.about_us

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ItemAdoutUsBinding
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.RewList
import real.erstate.realestateagency_1.data.model.RewSour

class Adapter(private val rewList: Resource<RewList>) : ListAdapter<RewSour,
        Adapter.ViewHolder>(DiffUtilNoteItemCallback()) {

    private val list = arrayListOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
           ItemAdoutUsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    var pos = 0
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        pos = position
        return holder.onBind(rewList.data?.results!![position])
    }

    inner class ViewHolder(private val binding: ItemAdoutUsBinding) :
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