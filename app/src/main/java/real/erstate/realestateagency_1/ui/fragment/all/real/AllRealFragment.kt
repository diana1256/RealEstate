package real.erstate.realestateagency_1.ui.fragment.all.real

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.ApartmentImage
import real.erstate.realestateagency_1.databinding.FragmentAllRealBinding
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateFragmentArgs
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateFragmentDirections
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateViewModel
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.AdapterViewPager


class AllRealFragment : Fragment() {

    private lateinit var binding: FragmentAllRealBinding
    private val viewModel: RealEstateViewModel by viewModel()

    private val args by navArgs<AllRealFragmentArgs>()
    private var isButtonClicked = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         binding = FragmentAllRealBinding.inflate(inflater,
            container,
            false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.shimmer.startShimmer()
        initView()
        onClik()
        onViewModel()
        return binding.root
    }

    fun onClik() {
        binding.item.ivHeart.setOnClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.item.ivHeart.setBackgroundResource(R.drawable.heart_red)

            } else {
                binding.item.ivHeart.setBackgroundResource(R.drawable.heart)
            }
        }

        binding.item.tvPh.setOnClickListener {
            val pri = args.yhnbv.lat
            val formattedNumber = pri.replace(".0+$".toRegex(), "")
            makePhoneCall(formattedNumber)
        }
    }

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }


    @SuppressLint("SetTextI18n")
    private fun onViewModel() {
        binding.con.isVisible = true
        binding.shimmer.isVisible = false
        viewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = false
        }
        Log.i("edfrgt", "onViewModel:${args.yhnbv.id}")
        viewModel.getApartment().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    Log.i("hbhuju", "onViewModel:${it.data}")
                    binding.item.tvTitle.setText(args.yhnbv.title)
                    binding.item.tvDesc.text = args.yhnbv.description
                    binding.item.tvDil.setText(args.yhnbv.type.title)
                    binding.item.tvLocation.text = args.yhnbv.address
                    binding.item.tvRoom.text = args.yhnbv.room_count
                    binding.item.tvKm.text = args.yhnbv.square
                    binding.item.tvSzsd.text = args.yhnbv.currency.symbol
                    binding.item.document.text = "Документы : ${args.yhnbv.document.title}"
                    binding.item.floor.text = "Этажность : ${args.yhnbv.floor.title}"
                    binding.item.comunikation.text = "Коммуникации : ${args.yhnbv.communications}"
                    binding.item.series.text = "Состояние :${args.yhnbv.series.title}"
                    val pri = args.yhnbv.price
                    val formattedNumber = pri.replace(".0+$".toRegex(), "")
                    binding.item.tvSan.text = formattedNumber
                    binding.item.tvName.text = args.yhnbv.author.first_name
                    val adapterViewPager = AdapterViewPager(requireActivity(),args.yhnbv)
                    Log.i("sxdcfr", "onViewModel:$it")
                    binding.con.isVisible = true
                    binding.item.vpv.adapter = adapterViewPager
                    binding.item.dotsIndicator.attachTo(binding.item.vpv)
                    adapterViewPager.setOnItemClickListener(object :
                        AdapterViewPager.OnItemClickListener {
                        override fun onItemClick(image: ApartmentImage) {
                           val  der = AllRealFragmentDirections.actionAllRealFragmentToPhotoAllFragment(args.yhnbv)
                                findNavController().navigate(der)

                        }
                    })
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(true)
                    Log.i("olerrt", "initViewModel:${it.message}")
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }


    private fun initView() {
        binding.item.bo.setOnClickListener {
            findNavController().navigate(R.id.allFragment)
        }
        binding.item.tvDil.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_photo, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            val v = view.findViewById<CardView>(R.id.card_iv)
            v.setOnClickListener {
            }
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(true)
            val btn = view.findViewById<MaterialButton>(R.id.btn_tri)
            btn.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    fun shepe(link: String) {
        val sengerind = Intent(Intent.ACTION_SEND)
        sengerind.type = "text/planin"
        sengerind.putExtra(Intent.EXTRA_TEXT, link)
        sengerind.`package` = "com.whatsapp"
        try {
            startActivity(sengerind)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

}