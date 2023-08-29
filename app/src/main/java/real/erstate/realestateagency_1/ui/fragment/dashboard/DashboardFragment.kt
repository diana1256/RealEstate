package real.erstate.realestateagency_1.ui.fragment.dashboard

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.entity.LoadRel
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.Favorite
import real.erstate.realestateagency_1.databinding.FragmentDashboardBinding
import real.erstate.realestateagency_1.ui.fragment.home.AdapterRealEstate
import real.erstate.realestateagency_1.ui.fragment.home.AdapterTwoLoad
import real.erstate.realestateagency_1.ui.util.Pref

class DashboardFragment : Fragment() {

    private lateinit var binding : FragmentDashboardBinding
    private val listLoad: ArrayList<LoadRel> = arrayListOf()
    private val viewModel : DashboardViewModel by viewModel()
    private var id = ""
    private var login = ""
    private var apartme = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        listLoad.add(
            LoadRel(
                image = 0,
                tvDil = "",
                tvTitle = "",
                tvSan = "",
                tvKm = "",
                tvLocation = "",
                tvRoom = "",
                id = ""
            )
        )
        listLoad.add(
            LoadRel(
                image = 0,
                tvDil = "",
                tvTitle = "",
                tvSan = "",
                tvKm = "",
                tvLocation = "",
                tvRoom = "",
                id = ""
            )
        )
        listLoad.add(
            LoadRel(
                image = 0,
                tvDil = "",
                tvTitle = "",
                tvSan = "",
                tvKm = "",
                tvLocation = "",
                tvRoom = "",
                id = ""
            )
        )
        listLoad.add(
            LoadRel(
                image = 0,
                tvDil = "",
                tvTitle = "",
                tvSan = "",
                tvKm = "",
                tvLocation = "",
                tvRoom = "",
                id = ""
            )
        )
        binding.itemD.tvSearch.ivSearch.visibility = View.VISIBLE
        val adapterLoad = AdapterTwoLoad()
        adapterLoad.submitList(listLoad)
        binding.load.rvRecom.adapter = adapterLoad
        on()
        onViewModel()
        initViews()
        wert()
        prover()
        onBtn()
        return binding.root
    }


    fun wert(){
        binding.itemD.tvb.text = Pref(requireContext()).isType()
        binding.itemD.dfg.text = Pref(requireContext()).isSena()
        binding.itemD.ff.text = Pref(requireContext()).isAdress()
        binding.itemD.fhh.text = Pref(requireContext()).isKm()
        binding.itemD.sdrt.text = Pref(requireContext()).isEtak()
        binding.itemD.fsds.text = Pref(requireContext()).isRoomCount()
        id = Pref(requireContext()).isAdressId()
    }
    private fun onClick(id:String){
        apartme = id
        val op = real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model(img = id)
        findNavController().navigate(R.id.realSearchFragment2, bundleOf(ARAR_ID to op))
    }

    private fun onLong(wer:String) {
        Toast.makeText(requireContext(), "Id apartment: $wer", Toast.LENGTH_SHORT).show()
    }
    private fun prover(){
        viewModel.searchUser(Pref(requireContext()).isLogin()).observe(requireActivity()){
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

        Log.i("sdef[;orgqq", "prover:${ binding.itemD.dfg.text},${binding.itemD.ff.text},${binding.itemD.fhh.text},${binding.itemD.sdrt.text},${binding.itemD.fsds.text},${binding.itemD.tvb.text}")
        if (binding.itemD.dfg.text.isNullOrEmpty() || binding.itemD.dfg.text.equals("$- $")){
            binding.itemD.df.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.kop.setOnClickListener {
                binding.itemD.df.isVisible = false
            }
        }
        if (binding.itemD.ff.text.isNullOrEmpty() || binding.itemD.ff.text.equals("$- $") ){
            binding.itemD.llThree.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.asdb.setOnClickListener {
                binding.itemD.llThree.isVisible = false
            }
        }
        if (binding.itemD.fhh.text.isNullOrEmpty() || binding.itemD.fhh.text.equals("$-$") ){
            binding.itemD.llFour.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.bnl.setOnClickListener {
                binding.itemD.llFour.isVisible = false
            }
        }
        if (binding.itemD.sdrt.text.isNullOrEmpty() ||  binding.itemD.sdrt.text.equals("$- $")){
            binding.itemD.llFife.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.ruj.setOnClickListener {
                binding.itemD.llFife.isVisible = false
            }
        }
        if (binding.itemD.fsds.text.isNullOrEmpty() ||  binding.itemD.fsds.text.equals("$- $")){
            binding.itemD.llFos.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.qhj.setOnClickListener {
                binding.itemD.llFos.isVisible = false
            }
        }

        if (binding.itemD.tvb.text.isNullOrEmpty() ||  binding.itemD.tvb.text.equals("$- $")){
            binding.itemD.llone.isVisible = false
        }else{
            binding.itemD.llVv.isVisible = false
            serarchFil()
            binding.itemD.qdv.setOnClickListener {
                binding.itemD.llone.isVisible = false
            }
        }
    }
    private fun on() {
        binding.itemD.conCard.setOnClickListener {
            findNavController().navigate(R.id.filterFragment)
        }
    }

    override fun onStop() {
        super.onStop()
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

    fun onBtn(){
        binding.itemD.g.setOnClickListener {
            binding.itemD.rty.isVisible = false
            binding.itemD.g.isVisible = false
            binding.itemD.llVv.isVisible = true
            binding.itemD.rvRecom.isVisible = true
            binding.itemD.rvSer.isVisible = false
            binding.itemD.rvFil.isVisible = false
            onViewModel()
        }
    }
    private fun onViewModel() {
        viewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = it
        }

        activity?.let { act ->
            viewModel.getApartment().observe(act) {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModel.loading.postValue(false)
                        val adapterRealEstate =  AdapterRealEstate(this::onClick,this::fav,this::onLong)
                        binding.itemD.rvRecom.adapter = adapterRealEstate
                        adapterRealEstate.submitList(it.data?.results)
                        binding.con.isVisible = true
                    }
                    Status.ERROR -> {
                        viewModel.loading.postValue(true)
                        Log.i("ololo", "initViewModel:${it.message}")
                    }
                    Status.LOADING -> viewModel.loading.postValue(true)
                }
            }
        }
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
        prover()
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
        prover()
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
        prover()
    }

    private fun initViews(){
        var img = ""
        binding.itemD.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.itemD.search.clearFocus()
                img = query
                activity?.let {
                    if (img.isEmpty()) {
                    } else {
                        viewModel.searFilter(img).observe(it) { data ->
                            when (data.status) {
                                Status.SUCCESS -> {
                                    viewModel.loading.postValue(false)
                                }
                                Status.ERROR -> {
                                    viewModel.loading.postValue(true)
                                }
                                Status.LOADING -> viewModel.loading.postValue(true)
                            }
                        }
                    }
                }
                return false
            }

            private fun onClick(id:String){
                apartme = id
                val op = real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model(img = id)
                findNavController().navigate(R.id.realSearchFragment2, bundleOf(ARAR_ID to op))
            }
            fun id(qwe:String){
                apartme = qwe
            }

            private fun onLong(wer:String) {
                Toast.makeText(requireContext(), "Id apartment: $wer", Toast.LENGTH_SHORT).show()
            }

            private fun fav(wer:Boolean,id: String){
                Log.i("sqwef", "fav:$wer")
                val fav = Favorite(user = login, apartment = id)
                Log.i("asdfr", "fav: $fav")
                Log.i("klder", "fav:$apartme")
                viewModel.setFavorite("Bearer ${Pref(requireContext()).isToken()}",fav).observe(requireActivity()){
                    when(it.status){
                        Status.SUCCESS -> {
                            Log.i("orinjge", "fav:$it")
                        }
                        Status.LOADING ->{}
                        Status.ERROR -> {}
                    }
                }
            }
            override fun onQueryTextChange(newText: String): Boolean {
                img = newText

                activity?.let { act ->
                    if (img.isEmpty()) {
                    }else {
                        viewModel.searFilter(img).observe(act) { data ->
                            when (data.status) {
                                Status.SUCCESS -> {
                                    viewModel.loading.postValue(false)
                                    binding.itemD.llVv.isVisible = false
                                    Log.i("pku", "onViewModel:$data")
                                    binding.itemD.g.isVisible = true
                                    binding.itemD.tvNumber.text = "${data.data?.count} объявления"
                                    val adapterRealEstate = AdapterRealEstate(this::onClick,this::fav,this::onLong)
                                    binding.itemD.rvSer.isVisible = true
                                    binding.itemD.rvSer.adapter = adapterRealEstate
                                    adapterRealEstate.submitList(data.data?.results)
                                    binding.itemD.rvRecom.isVisible = false
                                    binding.itemD.rvFil.isVisible = false
                                    Log.i(
                                        "ascdvf",
                                        "onQueryTextChange:${id},${binding.itemD.ff.text}"
                                    )
                                }
                                Status.ERROR -> {
                                    viewModel.loading.postValue(true)
                                }
                                Status.LOADING -> viewModel.loading.postValue(true)
                            }
                        }
                    }
                }
                return false
            }

        })
    }

    private fun serarchFil() {
        viewModel.loading.postValue(true)
        activity?.let { act ->
            viewModel.search(id, binding.itemD.fsds.text.toString())
                .observe(act) { data ->
                    when (data.status) {
                        Status.SUCCESS -> {
                            viewModel.loading.postValue(false)
                            val adaptefil =
                                AdapterRealEstate(this::onClick,this::fav,this::onLong)
                            adaptefil.submitList(data.data?.results)
                            binding.itemD.rvRecom.isVisible = false
                            binding.itemD.llVv.isVisible = false
                            binding.itemD.tvRecom.isVisible = false
                            binding.itemD.rvFil.adapter = adaptefil
                            binding.itemD.rvSer.isVisible = false
                            binding.itemD.rvFil.isVisible = true
                            binding.con.isVisible = true
                            binding.itemD.tvNumber.text = "${data.data?.count} объявления"
                            binding.itemD.g.isVisible = true
                            Log.i("idrrtt", "search:${data.data}")
                        }
                        Status.ERROR -> {
                            Log.i("olp", "onViewModel:${data.message} ")
                        }

                        Status.LOADING -> {
                            viewModel.loading.postValue(true)
                        }
                    }
                }
        }
    }

    companion object{
        const val ARAR_ID = "fbnop"
    }

    fun id(qwe:String){
        apartme = qwe
    }

    private fun fav(wer:Boolean,id: String){
        Log.i("sqwef", "fav:$wer")
        val fav = Favorite(user = login, apartment = id)
        Log.i("asdfr", "fav: $fav")
        Log.i("klder", "fav:$apartme")
        viewModel.setFavorite("Bearer ${Pref(requireContext()).isToken()}",fav).observe(requireActivity()){
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