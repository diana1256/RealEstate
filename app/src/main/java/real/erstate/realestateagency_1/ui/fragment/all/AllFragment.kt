package real.erstate.realestateagency_1.ui.fragment.all

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
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.databinding.FragmentAllBinding
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.ui.fragment.home.AdapterAll
import real.erstate.realestateagency_1.ui.fragment.home.AdapterRealEstate
import real.erstate.realestateagency_1.ui.fragment.home.AdapterTwoLoad
import real.erstate.realestateagency_1.ui.fragment.home.HomeFragmentDirections

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding
    private val   allViewModel : AllViewModel by viewModel()
    private val listTwoLoad = ArrayList<LoadRel>()
    private lateinit var adapterRealEstate : AdapterTwoLoad
    private val adapterTwoLoad = AdapterTwoLoad()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onViewModel()
        binding.shimmer.startShimmer()
        repeat(10) {
            listTwoLoad.add(
                LoadRel(
                    image = R.drawable.screensaver,
                    tvRoom = "",
                    tvLocation = "",
                    tvSan = "",
                    tvKm = "",
                    tvTitle = "",
                    tvDil = "",
                    id = ""
                )
            )
        }

        binding.load.rv.adapter = adapterTwoLoad
        adapterTwoLoad.submitList(listTwoLoad)
        onClick()
        return binding.root
    }

    fun onClick(){
        binding.item.def.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }

    private fun onViewModel(){
        allViewModel.loading.observe(requireActivity()){
            binding.shimmer.isVisible = it
        }

        allViewModel.getApartment().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    allViewModel.loading.postValue(false)
                    val fgh = it.data?.results!!.filter { it.best== true }
                    binding.con.isVisible = true
                    binding.item.rvAll.adapter = AdapterAll(requireActivity(),fgh,this::onClick)
                }
                Status.ERROR -> {
                    allViewModel.loading.postValue(true)
                    Log.i("ololo", "initViewModel:${it.message}")
                }
                Status.LOADING ->  allViewModel.loading.postValue(true)
            }
        }

    }

    private fun onClick(apartment: Apartment,id:String){
        val wer = Apartment(id = id,apartment.title,apartment.square,apartment.address,apartment.communications,apartment.description,apartment.best,apartment.price,apartment.room_count,apartment.lat,apartment.lng,apartment.currency,apartment.created_at,apartment.type,apartment.floor,apartment.document,apartment.series,apartment.region,apartment.apartment_images,apartment.author)
        val  sd = AllFragmentDirections.actionAllFragmentToAllRealFragment(wer)
        findNavController().navigate(sd)
    }
}
