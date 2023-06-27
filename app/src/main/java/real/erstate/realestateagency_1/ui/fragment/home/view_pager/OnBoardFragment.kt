package real.erstate.realestateagency_1.ui.fragment.home.view_pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentOnBoardBinding
import real.erstate.realestateagency_1.ui.fragment.home.view_pager.AdapterViewPager.Companion.ON_BOARD

class OnBoardFragment : Fragment() {

    private lateinit var binding : FragmentOnBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater,container,false)
        onBoard()
        return binding.root
    }

    private fun onBoard() {
        arguments.let {
            if (it != null){
                val data = it.getSerializable(ON_BOARD) as Model
                binding.ivPhoto.setImageResource(data.img)}
        }
    }

}