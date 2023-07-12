package real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentOnBoardVpBinding
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.AdapterViewPager.Companion.ON_BOR
import real.erstate.realestateagency_1.ui.util.loadImage
import real.erstate.realestateagency_1.data.model.ApartmentImage
import kotlin.math.log

class OnBoardVpFragment : Fragment(){

    private var  onItemClickListener: ((ApartmentImage) -> Unit)? = null
    private lateinit var binding: FragmentOnBoardVpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnBoardVpBinding.inflate(inflater, container, false)
        onBoard()
        return binding.root
    }

    private fun onBoard() {
       arguments.let {
           val data = it?.getSerializable(ON_BOR) as ApartmentImage
            binding.plo.loadImage(data.image)
            Log.i("iop", "onBoard:${data.image}")

           binding.plo.setOnClickListener {

               onItemClickListener?.invoke(data)
               Log.i("xdcfv", "onBoard:$data")

           }
        }
    }

   fun setOnItemClickListener(listener: (ApartmentImage) -> Unit){
       onItemClickListener = listener
   }
}