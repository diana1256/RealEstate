@file:Suppress("DEPRECATION")

package real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import real.erstate.realestateagency_1.data.model.ApartmentImage

class AdapterViewPager(
    fragment: FragmentActivity,
    private val listImage: List<ApartmentImage>
) : FragmentStateAdapter(fragment) {

    private var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int = listImage.size

    fun getTask(pos: Int): ApartmentImage {
        return listImage[pos]
    }

    override fun createFragment(position: Int): Fragment {
        val img = getTask(position)
        val data = bundleOf(ON_BOR to img)
        val fragment = OnBoardVpFragment()
        fragment.arguments = data

        fragment.setOnItemClickListener {
            onItemClickListener?.onItemClick(img)
        }

        return fragment
    }

    fun setOnItemClickListener(listener:  OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(image: ApartmentImage)
    }

    companion object {
        const val ON_BOR = "onBoard"
    }
}
