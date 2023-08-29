package real.erstate.realestateagency_1.ui.fragment.notifications

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.ui.fragment.home.AdapterTwoLoad
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.databinding.FragmentNotificationsBinding
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model
import real.erstate.realestateagency_1.ui.util.Pref

class NotificationsFragment : Fragment() {

    private lateinit var binding : FragmentNotificationsBinding
    private val   allViewModel : NotificationsViewModel by viewModel()
    private val listTwoLoad = ArrayList<LoadRel>()
    private val adapterTwoLoad = AdapterTwoLoad()
    private var idFav = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationsBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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
        onViewModel()
        return binding.root
    }


    private fun onViewModel(){
        allViewModel.loading.observe(requireActivity()){
            binding.shimmer.isVisible = it
        }
        Log.i("asdfg", "onViewModel:${Pref(requireContext()).isToken()}")
        allViewModel.getApartment("Bearer ${Pref(requireContext()).isToken()}").observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    allViewModel.loading.postValue(false)
                    binding.con.isVisible = true
                    Log.i("qjkloewfv", "onViewModel:${it}")
                   val adapterAll = it.data?.let { it1 -> AdapterFav(this::onId,it1,this::onClick,this::Fav) }
                    binding.item.rvH.adapter = adapterAll
                    binding.item.tvText.isVisible = adapterAll!!.itemCount <= 0
                }
                Status.ERROR -> {
                    allViewModel.loading.postValue(true)
                    Log.i("ololo", "initViewModel:${it.message}")
                }
                Status.LOADING ->  allViewModel.loading.postValue(true)
            }
        }
    }

    private fun onId(id:String){
        idFav = id
        Log.i("asdf", "onId:$id")
    }
    private fun onClick(id:String){
        val op = Model(img = id)
        findNavController().navigate(R.id.realFavFragment, bundleOf(ID_FAV_QWE to op))
    }
    private fun Fav(fav:Boolean){
        binding.con.isVisible = false
        allViewModel.loading.postValue(true)
        Log.i("qvbm", "Fav:$fav")
        allViewModel.delete("Bearer ${Pref(requireContext()).isToken()}",idFav).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    onViewModel()
                    allViewModel.loading.postValue(false)
                    Log.i("qswdefrghpl", "Fav:$it")
                }
                Status.ERROR ->{
                    allViewModel.loading.postValue(true)
                    Log.i("fklqd", "Fav:$it")
                }
                Status.LOADING ->{
                    allViewModel.loading.postValue(true)
                    Log.i("pkgeq", "Fav:$it")
                }
            }
        }
    }

    companion object{
        const val ID_FAV_QWE = "id_fav_sdf"
    }
}