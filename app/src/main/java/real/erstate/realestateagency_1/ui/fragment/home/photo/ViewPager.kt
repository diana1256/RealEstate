package real.erstate.realestateagency_1.ui.fragment.home.photo

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.ApartmentImage
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.OnBoardVpFragment

class ViewPager(fragment: FragmentActivity, private val listImage: Resource<Apartment>): FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int =  listImage.data?.apartment_images?.size!!

    fun getTask(pos:Int): ApartmentImage {
        return listImage.data?.apartment_images?.get(pos)!!
    }
    override fun createFragment(position: Int): Fragment {
        val img = getTask(position)
        val data = bundleOf(ON_BOR to img)
        val fragment = OnBoardVpFragment()
        fragment.arguments = data
        return fragment

    }
    companion object{
        const val ON_BOR = "onBoard"
    }
}