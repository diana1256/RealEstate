package real.erstate.realestateagency_1.ui.fragment.home

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.Favorite
import real.erstate.realestateagency_1.data.model.TokenObtainPair
import real.erstate.realestateagency_1.databinding.FragmentHomeBinding
import real.erstate.realestateagency_1.ui.fragment.home.view_pager.AdapterViewPager
import real.erstate.realestateagency_1.ui.fragment.home.view_pager.Model
import real.erstate.realestateagency_1.ui.util.Pref


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private val homeViewModel: HomeViewModel by viewModel ()
    private val delayTime = 5000L // 5 seconds
    private lateinit var runnable: Runnable
    private var id = ""
    private val adapterOneLoad = AdapterOneLoad()
    private var login = ""
    private var apartme = ""
    private var token = ""
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
        repeat(10) {
            listTwoLoad.add(
                LoadRel(
                    image = 0,
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
                    image = 0,
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
         homeViewModel.searchUser(Pref(requireContext()).isLogin()).observe(requireActivity()){
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
        val qwe = TokenObtainPair(login= Pref(requireContext()).isLogin(), password = Pref(requireContext()).isPasword())
        Log.i("aqwert", "olp:$qwe")
        homeViewModel.token(qwe).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS ->{
                    Log.i("apple", "olp:$it")
                    token = it.data?.access.toString()
                    Pref(requireContext()).setToken(token)
                }
                Status.LOADING->{
                    Log.i("scdvfbg", "olp:$it")
                }
                Status.ERROR -> Log.i("swdef", "olp:$it")
            }
        }
        if (binding.iem.tvSan.text.isNullOrEmpty() || binding.iem.tvSan.text.equals("$- $")) {
            binding.iem.llOne.isVisible = false
        } else {
            sir()
           // binding.con.isVisible = true
            binding.iem.ivGone.setOnClickListener {
                binding.iem.llOne.isVisible = false
            }
        }
        if (binding.iem.adres.text.isEmpty() ||  binding.iem.adres.text.equals("$-$")) {
            binding.iem.tyu.isVisible = false
        } else {
            sir()
          //  binding.con.isVisible = true
            binding.iem.ref.setOnClickListener {
                binding.iem.tyu.isVisible = false
            }
        }
        if (binding.iem.count.text.isEmpty() ||  binding.iem.count.equals("$-$")) {
            binding.iem.llThree.isVisible = false
        } else {
            sir()
           // binding.con.isVisible = true
            binding.iem.ivGaneIv.setOnClickListener {
                binding.iem.llThree.isVisible = false
            }
        }
        if (binding.iem.square.text.isEmpty() || binding.iem.square.text.equals("$-$")) {
            binding.iem.yuo.isVisible = false
        } else {
            sir()
          //  binding.con.isVisible = true
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


    private fun id(id:String){
        apartme = id
    }

    private fun onLong(wer:String) {
        Toast.makeText(requireContext(), "Id apartment: $wer", Toast.LENGTH_SHORT).show()
    }
    private fun onClick(wer:String) {
        val op = real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model(img = wer)
        findNavController().navigate(R.id.realEstateFragment, bundleOf(ID_PAR to op))
    }

    private fun onClickOne(wqe:String) {
        val op = real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model(img = wqe)
        findNavController().navigate(R.id.realEstateFragment, bundleOf(ID_PAR to op))

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
                   val adapter = AdapterRealEstate(this::onClick,this::fav,this::onLong)
                    Log.i("raqwen", "sir: $apartme")
                    binding.con.isVisible = true
                    adapter.submitList(it.data?.results)
                    val fgh = it.data?.results?.filter { it.best== true }
                    Log.i("bnm,,", "onViewModel $fgh")
                    binding.iem.rvTwo.adapter =adapter
                    adapterOne =  AdapterOne(this::onClickOne,this::fav,this::onLong)
                    Log.i("ploo", "onViewModel: $it")
                    binding.iem.rvOne.adapter = adapterOne
                    Log.i("rpil", "sir: $apartme")
                    adapterOne.submitList(fgh)
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
        homeViewModel.loading.postValue(true)
        activity?.let { act ->
            if (id.isEmpty() || binding.iem.count.text.isEmpty()){
            }else {
                homeViewModel.search(id, binding.iem.count.text.toString())
                    .observe(act) { dataSearch ->
                        when (dataSearch.status) {
                            Status.SUCCESS -> {
                                homeViewModel.loading.postValue(false)
                                adapter = AdapterRealEstate(this::onClick,this::fav,this::onLong)
                                Log.i("rain", "sir: $apartme")
                                adapter.submitList(dataSearch.data?.results)
                                binding.iem.rvQwerk.isVisible = true
                                binding.iem.rvTwo.isVisible = false
                                binding.iem.rvQwerk.adapter = adapter
                                binding.con.isVisible = true
                                binding.iem.tvNumber.text = "${dataSearch.data?.count.toString()}  обьявления"
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

    companion object{
        const val ID_PAR = "idPar"
    }

    private fun fav(wer:Boolean,id:String){
        Log.i("sqwef", "fav:$wer")
        val fav = Favorite(user = login, apartment = id)
        Log.i("asdfr", "fav: $fav")
        Log.i("lkiop", "fav:$token")
        Pref(requireContext()).setToken(token)
        Log.i("klder", "fav:$apartme")
        homeViewModel.setFavorite("Bearer $token",fav).observe(requireActivity()){
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