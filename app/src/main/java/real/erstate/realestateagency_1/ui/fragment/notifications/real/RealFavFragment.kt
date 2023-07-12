package real.erstate.realestateagency_1.ui.fragment.notifications.real

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
import real.erstate.realestateagency_1.databinding.FragmentRealFavBinding
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.AdapterViewPager


class RealFavFragment : Fragment() {

    private lateinit var binding:FragmentRealFavBinding
    private val viewModel: FavRealViewModel by viewModel()

    private val args by navArgs<RealFavFragmentArgs>()
    private var isButtonClicked = false
    var g = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRealFavBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding.shimmer.startShimmer()
        initView()
        onClik()
        return binding.root
    }

    fun onClik() {
        binding.item.ivHeart.setOnClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.item.ivHeart.setBackgroundResource(R.drawable.heart)

            } else {
                binding.item.ivHeart.setBackgroundResource(R.drawable.heart_red)
            }
        }

        binding.item.tvPh.setOnClickListener {
            makePhoneCall("0550900700")
        }
    }

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun onViewModel() {
        binding.con.isVisible = true
        binding.shimmer.isVisible = false
        viewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = false
        }

        viewModel.getImage(args.lkjh.status).observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.con.isVisible = true
                    viewModel.loading.postValue(false)
                    Log.i("hbhuju", "onViewModel:${it.data}")
                    binding.item.tvTitle.setText(it.data?.title)
                    binding.item.tvDesc.text = it.data?.description
                    binding.item.tvDil.setText(it.data?.type?.title)
                    binding.item.tvLocation.text = it.data?.address
                    binding.item.tvRoom.text = it.data?.room_count
                    binding.item.tvKm.text =  it.data?.square
                    binding.item.tvSzsd.text = it.data?.currency?.symbol
                    binding.item.document.text = "Документы : ${it.data?.document?.title}"
                    binding.item.floor.text = "Этажность : ${it.data?.floor?.title}"
                    binding.item.comunikation.text = "Коммуникации : ${it.data?.communications}"
                    binding.item.series.text = "Состояние :${it.data?.series?.title}"
                    val pri = it.data?.price
                    val formattedNumber = pri?.replace(".0+$".toRegex(), "")
                    binding.item.tvSan.text = formattedNumber
                    binding.item.tvName.text = it.data?.author?.first_name
                   // val adapterViewPager = AdapterViewPager(requireActivity(),args.lkjh)
                    Log.i("sxdcfr", "onViewModel:$it")
                   // binding.item.vpv.adapter = adapterViewPager
                    binding.item.dotsIndicator.attachTo(binding.item.vpv)
                    g = it.data?.apartment_images.toString()
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
            findNavController().navigate(R.id.navigation_notifications)
        }
        binding.item.tvDil.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_photo, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            val v = view.findViewById<CardView>(R.id.card_iv)
            v.setOnClickListener {
                shepe(g)
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