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
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.ApartmentImage
import real.erstate.realestateagency_1.databinding.FragmentAllRealBinding
import real.erstate.realestateagency_1.ui.fragment.all.AllFragment.Companion.ID_ALL
import real.erstate.realestateagency_1.ui.fragment.home.HomeFragment
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateFragment
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateViewModel
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.AdapterViewPager
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.Model


class AllRealFragment : Fragment() {

    private lateinit var binding: FragmentAllRealBinding
    private val viewModel: AllRealViewModel by viewModel()
    private var isButtonClicked = false
    private lateinit var model: Model


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllRealBinding.inflate(inflater,
            container,
            false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initView()
        onClik()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewModel()
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
    }

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun onViewModel() {
        viewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = it
        }
        if (arguments != null) {
            model = arguments?.getSerializable(ID_ALL) as Model
            Log.i("cdvfbgnh", "onViewModel:${model.img}")
            viewModel.getApartment(model.img).observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModel.loading.postValue(false)
                        Log.i("hbhuju", "onViewModel:${it.data}")
                        binding.item.tvTitle.setText(it.data?.title)
                        binding.item.tvDesc.text = it.data?.description
                        binding.item.tvDil.setText(it.data?.type?.title)
                        binding.item.tvLocation.text = it.data?.address
                        binding.item.tvRoom.text = it.data?.room_count
                        binding.item.tvKm.text = it.data?.square
                        binding.item.document.text = "Документы : ${it.data?.document?.title}"
                        binding.item.floor.text = "Этажность : ${it.data?.floor?.title}"
                        binding.item.comunikation.text = "Коммуникации : ${it.data?.communications}"
                        binding.item.series.text = "Состояние :${it.data?.series?.title}"
                        val pri = it.data?.price
                        binding.item.tvSzsd.text = it.data?.currency?.symbol
                        val formattedNumber = pri?.replace(".0+$".toRegex(), "")
                        binding.item.tvSan.text = formattedNumber
                        binding.item.tvName.text = it.data?.author?.first_name
                        binding.con.isVisible = true
                        val adapterViewPager =
                            it.data?.let { it1 -> AdapterViewPager(requireActivity(), it1) }
                        Log.i("sxdcfr", "onViewModel:${it.data}")
                        binding.item.vpv.adapter = adapterViewPager
                        binding.item.dotsIndicator.attachTo(binding.item.vpv)
                        val lat = it.data?.lat
                        adapterViewPager?.setOnItemClickListener(object :
                            AdapterViewPager.OnItemClickListener {
                            override fun onItemClick(image: ApartmentImage) {
                                val mi = Model(img = it.data.id)
                                findNavController().navigate(R.id.photoAllFragment, bundleOf(
                                    ID_ALL_REAL to mi)
                                )
                            }
                        })
                        binding.item.tvPh.setOnClickListener {
                            val formattedNumber = lat?.replace(".0+$".toRegex(), "")
                            if (formattedNumber != null) {
                                makePhoneCall(formattedNumber)
                            }
                        }
                    }
                    Status.ERROR -> {
                        viewModel.loading.postValue(true)
                        Log.i("olerrt", "initViewModel:${it.message}")
                    }
                    Status.LOADING -> viewModel.loading.postValue(true)
                }
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

    companion object {
        const val ID_ALL_REAL ="id_all_real"
    }
}