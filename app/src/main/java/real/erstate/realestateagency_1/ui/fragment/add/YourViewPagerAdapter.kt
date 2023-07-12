import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import real.erstate.realestateagency_1.databinding.AddRvItemBinding
import real.erstate.realestateagency_1.databinding.FragmentOnBoardBinding

class YourViewPagerAdapter: RecyclerView.Adapter<YourViewPagerAdapter.PhotoViewHolder>() {

    private val list = arrayListOf<Uri>()

    @SuppressLint("NotifyDataSetChanged")
    fun addTask(listTask: Uri) {
        list.clear()
        list.addAll(listOf(listTask))
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(AddRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PhotoViewHolder(val binding: AddRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photoPart: Uri) {
            Glide.with(binding.plo).load(photoPart).into(binding.plo)
        }
    }
}
