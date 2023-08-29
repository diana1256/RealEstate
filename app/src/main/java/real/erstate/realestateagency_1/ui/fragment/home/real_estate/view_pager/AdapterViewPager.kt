@file:Suppress("DEPRECATION")

package real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.ApartmentImage

class AdapterViewPager(
    fragment: FragmentActivity,
    private val listImage: Apartment
) : FragmentStateAdapter(fragment) {

    private var onItemClickListener: OnItemClickListener? = null



    override fun getItemCount(): Int =  listImage?.apartment_images?.size ?: 0

    fun getTask(pos: Int): ApartmentImage {
        return listImage.apartment_images[pos]
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