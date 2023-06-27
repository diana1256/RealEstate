package real.erstate.realestateagency_1.ui.fragment.home

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

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private val homeViewModel: HomeViewModel by viewModel ()
    private val delayTime = 5000L // 5 seconds
    private lateinit var runnable: Runnable
    private val adapterOneLoad = AdapterOneLoad()
    private val adapterTwoLoad = AdapterTwoLoad()
    private val listOneLoad = ArrayList<LoadRel>()
    private val listTwoLoad = ArrayList<LoadRel>()
    private lateinit var adapterRealEstate: AdapterRealEstate
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
        olp()
        return binding.root
    }

    private fun initListener() {
        binding.iem.tvPv.setOnClickListener {
            findNavController().navigate(R.id.allFragment)
        }
        binding.iem.filter.setOnClickListener {
            findNavController().navigate(R.id.filterFragment)
        }
    }


    private fun onViewModel() {
        homeViewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = it
        }
        homeViewModel.getApartment().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    homeViewModel.loading.postValue(false)
                    adapterRealEstate = AdapterRealEstate(requireActivity(), it, this::onClick)
                    adapterOne = AdapterOne(requireActivity(), it, this::onClickOne)
                    Log.i("ploo", "onViewModel: $it")
                    binding.con.isVisible = true
                    binding.iem.rvTwo.adapter = adapterRealEstate
                    binding.iem.rvOne.adapter = adapterOne

                    adapterRealEstate.submitList(it.data?.results)
                }

                Status.ERROR -> {
                    homeViewModel.loading.postValue(true)
                    Log.i("ololo", "initViewModel:${it.message}")
                }

                Status.LOADING -> homeViewModel.loading.postValue(true)
            }
        }
    }

    private fun onClick(apartment: Apartment) {
        val  sd = HomeFragmentDirections.actionNavigationHomeToRealEstateFragment2(apartment)
        findNavController().navigate(sd)
    }

    private fun onClickOne(apartment: Apartment) {
       val dfg = HomeFragmentDirections.actionNavigationHomeToRealEstateFragment2(apartment)
        findNavController().navigate(dfg)
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


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }


    private fun olp() {
        if (binding.iem.tvSan.text.isEmpty()) {
            binding.iem.llOne.isVisible = false
        } else {
            binding.iem.ivGone.setOnClickListener {
                binding.iem.llOne.isVisible = false
            }
        }
        if (binding.iem.adres.text.isEmpty()) {
            binding.iem.tyu.isVisible = false
        } else {
            binding.iem.ref.setOnClickListener {
                binding.iem.tyu.isVisible = false
            }
        }
        if (binding.iem.count.text.isEmpty()) {

            binding.iem.llThree.isVisible = false
        } else {
            binding.iem.ivGaneIv.setOnClickListener {
                binding.iem.llThree.isVisible = false
            }
        }
        if (binding.iem.square.text.isEmpty()) {
            binding.iem.yuo.isVisible = false

        } else {
            binding.iem.ivOlp.setOnClickListener {
                binding.iem.yuo.isVisible = false
            }
        }
    }

}