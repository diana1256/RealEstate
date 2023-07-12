package real.erstate.realestateagency_1.ui.fragment.home

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.databinding.FragmentHomeBinding
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.ui.fragment.home.view_pager.AdapterViewPager
import real.erstate.realestateagency_1.ui.util.Pref

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private val homeViewModel: HomeViewModel by viewModel ()
    private val delayTime = 5000L // 5 seconds
    private lateinit var runnable: Runnable
    private var id = ""
    private val adapterOneLoad = AdapterOneLoad()
    private var idRv = ""
    private val adapterTwoLoad = AdapterTwoLoad()
    private val listOneLoad = ArrayList<LoadRel>()
    private val listTwoLoad = ArrayList<LoadRel>()
    private lateinit var adapterRealEstate: AdapterRealEstate
    private lateinit var adapter: AdapterRealEstate
    private lateinit var adapterOne: AdapterOne

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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

        repeat(4) {
            listOneLoad.add(
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

        binding.load.rvOne.adapter = adapterOneLoad
        adapterOneLoad.submitList(listOneLoad)
        binding.load.rvTwo.adapter = adapterTwoLoad
        adapterTwoLoad.submitList(listTwoLoad)
        onViewModel()
        initListener()
        or()
        olp()
        return binding.root
    }

    private fun olp() {
        if (binding.iem.tvSan.text.isNullOrEmpty() || binding.iem.tvSan.text.equals("$- $")) {
            binding.iem.llOne.isVisible = false
        } else {
            sir()
            binding.iem.ivGone.setOnClickListener {
                binding.iem.llOne.isVisible = false
            }
        }
        if (binding.iem.adres.text.isEmpty() ||  binding.iem.adres.text.equals("$-$")) {
            binding.iem.tyu.isVisible = false
        } else {
            sir()
            binding.iem.ref.setOnClickListener {
                binding.iem.tyu.isVisible = false
            }
        }
        if (binding.iem.count.text.isEmpty() ||  binding.iem.count.equals("$-$")) {
            binding.iem.llThree.isVisible = false
        } else {
            sir()
            binding.iem.ivGaneIv.setOnClickListener {
                binding.iem.llThree.isVisible = false
            }
        }
        if (binding.iem.square.text.isEmpty() || binding.iem.square.text.equals("$-$")) {
            binding.iem.yuo.isVisible = false
        } else {
            sir()
            binding.iem.ivOlp.setOnClickListener {
                binding.iem.yuo.isVisible = false
            }
        }
    }

    fun or(){
        binding.iem.tvSan.setText(Pref(requireContext()).isSena())
        binding.iem.adres.setText(Pref(requireContext()).isAdress())
        binding.iem.count.setText(Pref(requireContext()).isRoomCount())
        binding.iem.square.setText(Pref(requireContext()).isKm())
        id = Pref(requireContext()).isAdressId()
    }
    private fun initListener() {
        binding.iem.tvPv.setOnClickListener {
            findNavController().navigate(R.id.allFragment)
        }
       binding.iem.filter.setOnClickListener {
            findNavController().navigate(R.id.filterFragment)
        }
    }


    private fun onClick(apartment: Apartment,id:Int,wer:String) {
        Log.i("tgyh", "onClick:$idRv")
        val wer = Apartment(id = wer,apartment.title,apartment.square,apartment.address,apartment.communications,apartment.description,apartment.best,apartment.price,apartment.room_count,apartment.lat,apartment.lng,apartment.currency,apartment.created_at,apartment.type,apartment.floor,apartment.document,apartment.series,apartment.region,apartment.apartment_images,apartment.author)
        val  sd = HomeFragmentDirections.actionNavigationHomeToRealEstateFragment2(wer)
        findNavController().navigate(sd)
    }

    private fun onClickOne(apartment: Apartment,id: Int,wqe:String) {
        val wer = Apartment(id = wqe,apartment.title,apartment.square,apartment.address,apartment.communications,apartment.description,apartment.best,apartment.price,apartment.room_count,apartment.lat,apartment.lng,apartment.currency,apartment.created_at,apartment.type,apartment.floor,apartment.document,apartment.series,apartment.region,apartment.apartment_images,apartment.author)
        val dfg = HomeFragmentDirections.actionNavigationHomeToRealEstateFragment2(wer)
        findNavController().navigate(dfg)
    }
    @SuppressLint("SetTextI18n")
    private fun onViewModel() {
        homeViewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = it
        }
        homeViewModel.getApartment().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    homeViewModel.loading.postValue(false)
                     adapterRealEstate = AdapterRealEstate(requireActivity(),it,"1", this::onClick)
                    binding.con.isVisible = true
                    val fgh = it.data?.results?.filter { it.best== true }
                    Log.i("bnm,,", "onViewModel $fgh")
                    binding.iem.rvTwo.adapter = adapterRealEstate

                    adapterOne = fgh?.let { it1 ->
                        AdapterOne(requireActivity(),
                            it1,"1", this::onClickOne)
                    }!!
                    Log.i("ploo", "onViewModel: $it")
                    binding.iem.rvOne.adapter = adapterOne
                    Log.i("serty", "onViewModel:${binding.iem.adres.text},$id")
                }

                Status.ERROR -> {
                    homeViewModel.loading.postValue(true)
                    Log.i("ololo", "initViewModel:${it.message}")
                }
                Status.LOADING -> homeViewModel.loading.postValue(true)
            }
        }
    }

    private fun sir(){
        activity?.let { act ->
            if (id.isEmpty() || binding.iem.count.text.isEmpty()){
            }else {
                homeViewModel.search(id, binding.iem.count.text.toString())
                    .observe(act) { dataSearch ->
                        when (dataSearch.status) {
                            Status.SUCCESS -> {
                                homeViewModel.loading.postValue(false)
                                adapter = AdapterRealEstate(act, dataSearch, "12", this::onClick)
                                binding.iem.rvQwerk.isVisible = true
                                binding.iem.rvTwo.isVisible = false
                                binding.iem.rvQwerk.adapter = adapter
                                binding.iem.tvNumber.text =
                                    "${dataSearch.data?.count.toString()}  обьявления"

                                Log.i("idtt", "search:${dataSearch.data}")
                            }
                            Status.ERROR -> {
                                Log.i("olp", "onViewModel:${dataSearch.message} ")
                            }

                            Status.LOADING -> {
                                homeViewModel.loading.postValue(true)
                            }
                        }
                    }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterViewPager(requireActivity())
        binding.iem.vpv.adapter = adapter
        binding.iem.dotsIndicator.attachTo(binding.iem.vpv)
        handler = Handler()
        runnable = Runnable {
            val currentItem = binding.iem.vpv.currentItem
            val totalItems = binding.iem.vpv.adapter?.itemCount ?: 0
            val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0
            binding.iem.vpv.setCurrentItem(nextItem, true)
            handler.postDelayed(runnable, delayTime)
        }
        handler.postDelayed(runnable, delayTime)
    }
    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
        sap.remove("count")
        sap.apply()
    }

    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacks(runnable)
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
        sap.remove("count")
        sap.apply()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
        sap.remove("count")
        sap.apply()
    }

    override fun onResume() {
        super.onResume()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
        sap.remove("count")
        sap.apply()
        olp()
    }

    override fun onPause() {
        super.onPause()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
        sap.remove("count")
        sap.apply()
        olp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val sap = Pref(requireContext()).sharedPref.edit()
        sap.remove("adress")
        sap.remove("type")
        sap.remove("sena")
        sap.remove("km")
        sap.remove("adre")
        sap.remove("etak")
         sap.remove("count")
        sap.apply()
        olp()
    }
}