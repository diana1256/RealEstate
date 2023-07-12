package real.erstate.realestateagency_1.ui.fragment.about_us.raiting

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.databinding.FragmentReitingBinding
import real.erstate.realestateagency_1.ui.fragment.about_us.Adapter

class ReitingFragment : Fragment() {

    private lateinit var binding:  FragmentReitingBinding
    val viewModel: ReitingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReitingBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initView()
        return binding.root
    }

    private fun initView() {
        binding.g.setOnClickListener {
            findNavController().navigate(R.id.navigation_about_us)
        }
        viewModel.loading.observe(requireActivity()) {
            binding.progresBar.isVisible = it
        }
        viewModel.getRew().observe(requireActivity()){
            when(it.status){
                Status.SUCCESS->{
                    viewModel.loading.postValue(false)
                    binding.toolbarFavorite.visibility = View.VISIBLE
                    val adapter = AdapterRei(it)
                    binding.rvRe.adapter = adapter
                    Log.i("kjnyuih", "inttView:${it.data}")
                }
                Status.LOADING->{
                    viewModel.loading.postValue(true)
                }
                Status.ERROR->{
                    viewModel.loading.postValue(true)
                }
            }
        }
    }
}