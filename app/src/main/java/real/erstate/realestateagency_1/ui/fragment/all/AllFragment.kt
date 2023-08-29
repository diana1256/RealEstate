package real.erstate.realestateagency_1.ui.fragment.all

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.Favorite
import real.erstate.realestateagency_1.databinding.FragmentAllBinding
import real.erstate.realestateagency_1.ui.fragment.home.AdapterAll
import real.erstate.realestateagency_1.ui.fragment.home.AdapterRealEstate
import real.erstate.realestateagency_1.ui.fragment.home.AdapterTwoLoad
import real.erstate.realestateagency_1.ui.fragment.home.HomeFragmentDirections
import real.erstate.realestateagency_1.ui.util.Pref

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding
    private val   allViewModel : AllViewModel by viewModel()
    private val listTwoLoad = ArrayList<LoadRel>()
    private val adapterTwoLoad = AdapterTwoLoad()
    private var login = ""
    private var apartme = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onViewModel()
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
        allViewModel.searchUser(Pref(requireContext()).isLogin()).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS ->{
                    login = it.data?.results?.get(0)?.id.toString()
                }
                Status.LOADING->{
                    Log.i("scdvfbg", "olp:$it")
                }
                Status.ERROR -> Log.i("swdef", "olp:$it")
            }
        }

        allViewModel.loading.observe(requireActivity()){
            binding.shimmer.isVisible = it
        }

        allViewModel.getApartment().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    allViewModel.loading.postValue(false)
                    val fgh = it.data?.results!!.filter { it.best== true }
                    binding.con.isVisible = true
                    val adapterAll = AdapterAll(this::onClick,this::fav,this::onLong)
                    binding.item.rvAll.adapter = adapterAll
                    adapterAll.submitList(fgh)
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
        apartme = id
    }

    private fun onLong(wer:String) {
        Toast.makeText(requireContext(), "Id apartment: $wer", Toast.LENGTH_SHORT).show()
    }
    private fun onClick(id:String){
        apartme = id
        val op = real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model(img = id)
        findNavController().navigate(R.id.allRealFragment, bundleOf(ID_ALL to op))
    }

    companion object{
        const val ID_ALL = "id_all"
    }

    private fun fav(wer:Boolean,id: String){
        Log.i("sqwef", "fav:$wer")
        val fav = Favorite(user = login, apartment = id)
        Log.i("asdfr", "fav: $fav")
        Log.i("klder", "fav:$apartme")
        allViewModel.setFavorite("Bearer ${Pref(requireContext()).isToken()}",fav).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS -> {
                    Log.i("orinjge", "fav:$it")
                }
                Status.LOADING ->{}
                Status.ERROR -> {}
            }
        }
    }
    }
