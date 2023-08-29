package real.erstate.realestateagency_1.ui.fragment.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.databinding.ItemLoadingBinding


class AdapterTwoLoad : ListAdapter<LoadRel,
        AdapterTwoLoad.NoteViewHolder>(DiffUtilNoteItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(note: LoadRel) {
           /* binding.tvTitle.text = note.tvTitle
             binding.tvGrey.text = note.tvRoom
            binding.tvLp.text = note.tvLocation
            binding.tvLoadObav.text = note.tvSan
            binding.tvDil.text = note.tvDil
*/
        }
    }

    private class DiffUtilNoteItemCallback : DiffUtil.ItemCallback<LoadRel>() {
        override fun areItemsTheSame(oldItem: LoadRel, newItem: LoadRel): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: LoadRel, newItem: LoadRel): Boolean {
            return oldItem == newItem
        }
    }
}