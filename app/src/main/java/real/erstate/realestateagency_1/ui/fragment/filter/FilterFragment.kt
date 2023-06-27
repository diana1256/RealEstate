package real.erstate.realestateagency_1.ui.fragment.filter

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentFilterBinding
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Region

class FilterFragment() : Fragment(){
    private val viewModel : FilterViewModel by  viewModel()
    private lateinit var binding: FragmentFilterBinding
    private var adress = ""
    private var room = ""
    private var km = ""
    private var h = ""
    private var sena = ""
    private var isButtonClicked = false
    private var isButtonClcked = false
    private var isButtonCcked = false
    private var isButtCcked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilterBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onViewModel()
        onClikListene()
        rv()
        okk()
        olp()
        return binding.root
    }

    private fun okk(){
        km = "${binding.etOne.text.toString()}$-${binding.etTwo.text.toString()}$"
        binding.cardIv.setOnClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.cardIv.setBackgroundResource(R.drawable.bg_lyue)
                binding.ivIcon.setImageResource(R.drawable.shop_card)
            } else {
                binding.ivIcon.setImageResource(R.drawable.shop_fil)
                binding.cardIv.setBackgroundResource(R.drawable.bg_fav_btn)
            }
        }

        binding.cardF.setOnClickListener {
            isButtonClcked = !isButtonClcked
            if (isButtonClcked) {
                binding.cardF.setBackgroundResource(R.drawable.bg_lyue)
                binding.ivScreensaver.setImageResource(R.drawable.elite_card)
            } else {
                binding.ivScreensaver.setImageResource(R.drawable.elite_fil)
                binding.cardF.setBackgroundResource(R.drawable.bg_fav_btn)
            }
        }

        binding.cardOlo.setOnClickListener {
            isButtonCcked = !isButtonCcked
            if (isButtonCcked) {
                binding.cardOlo.setBackgroundResource(R.drawable.bg_lyue)
                binding.ivScr.setImageResource(R.drawable.secyn_wr)
            } else {
                binding.ivScr.setImageResource(R.drawable.secondary_fil)
                binding.cardOlo.setBackgroundResource(R.drawable.bg_fav_btn)
            }
        }

        binding.cardOpo.setOnClickListener {
            isButtCcked = !isButtCcked
            if (isButtCcked) {
                binding.cardOpo.setBackgroundResource(R.drawable.bg_lyue)
                binding.ivOi.setImageResource(R.drawable.home_wr)
            } else {
                binding.ivOi.setImageResource(R.drawable.house_fil)
                binding.cardOpo.setBackgroundResource(R.drawable.bg_fav_btn)
            }
        }
    }

    private fun olp(){
        binding.searchBar.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            val minValue = values[0].toInt()
            val maxValue = values[1].toInt()
            binding.tvObav.text = "${minValue.toString()} м2"
            binding.tvSquare.text = if (maxValue == slider.valueTo.toInt()) {
                "$maxValue+ м2"
            } else {
                "${maxValue.toString()} м2"
            }
            km = "${binding.tvObav.text.toString()}-${binding.tvSquare.text.toString()}"
        }

        binding.searchBarTwo.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            val minValue = values[0].toInt()
            val maxValue = values[1].toInt()
            binding.tvOb.text = minValue.toString()
            binding.tvSq.text =  if (maxValue == slider.valueTo.toInt()) {
                "$maxValue+"
            } else {
                "${maxValue.toString()}"
            }

        }
    }
    private fun rv() {
        val list = arrayListOf<String>()
        list.add("1 (8)")
        list.add("2 (9)")
        list.add("3 (5)")
        list.add("4 (3)")
        list.add("5 (2)")
        list.add("6+ (1)")
        val adapterFilter = AdapterRoom(this::onRoom)
        binding.rvRoom.adapter = adapterFilter
        adapterFilter.load(list)
    }

    private fun onClikListene() {
        binding.btnAdFil.setOnClickListener {
            sena = "${binding.etOne.text}$-${binding.etTwo.text}$"
            isButtCcked = !isButtCcked
            if (isButtCcked) {
                binding.btnAdFil.setBackgroundResource(R.drawable.bg_lyue)
            }
            findNavController().navigate(R.id.navigation_home)
            search()
        }
    }

    private fun onViewModel() {
        viewModel.getRegion().observe(requireActivity()){
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rv.adapter = RegionAdapter(it,this::type)
                    Log.i("okijuhyg", "onViewModel:${it.data}")
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }

    private fun onRoom(pos: String){
        val firstValue = pos.split(" ")[0]
        room = firstValue
        Log.i("cdvf", "onRoom:$room")
    }

    private fun type(pos: Region){
        adress = pos.id.toString()
        Log.i("okijuhy", "type:$adress")
        h = pos.name
    }

    fun search(){
        viewModel.search(adress,room).observe(requireActivity()){
            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("ijuhygt", "search:${it.data}")
                }
                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }
}
