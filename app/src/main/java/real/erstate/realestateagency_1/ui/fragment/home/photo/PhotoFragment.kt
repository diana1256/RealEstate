
package real.erstate.realestateagency_1.ui.fragment.home.photo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
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
import real.erstate.realestateagency_1.databinding.FragmentPhotoBinding
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateFragmentArgs

import real.erstate.realestateagency_1.ui.fragment.home.real_estate.view_pager.AdapterViewPager
import kotlin.random.Random

class PhotoFragment : Fragment() {

    private lateinit var binding : FragmentPhotoBinding

    var img = ""
    private val args by navArgs<PhotoFragmentArgs>()

    private val viewModel : PhotoViewModel by viewModel()
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPhotoBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initView()
        onViewModel()
        return binding.root
    }

    private fun onViewModel(){
        viewModel.loading.observe(requireActivity()){
            binding.progresBar.isVisible = it
        }
        viewModel.getImage(args.qwe.id).observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    Log.i("zasxd", "onViewModel:${args.qwe}")
                    val adapterViewPager = AdapterViewPager(requireActivity(),args.qwe)
                    binding.vpv.adapter = adapterViewPager
                    binding.dotsIndicator.attachTo(binding.vpv)
                    val wer = args.qwe.apartment_images
                    val  er = wer[0]
                    img = er.image
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
        binding.ivBlack.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.alertTv.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_photo, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setGravity(Gravity.BOTTOM)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(true)
            val btn = view.findViewById<MaterialButton>(R.id.btn_tri)
            btn.setOnClickListener {
                dialog.dismiss()
            }
            val v = view.findViewById<CardView>(R.id.card_iv)
            v.setOnClickListener {
                shepe(img)
            }
        }
    }

    fun shepe(link:String){
        val sengerind = Intent(Intent.ACTION_SEND)
        sengerind.type = "text/planin"
        sengerind.putExtra(Intent.EXTRA_TEXT,link)
        sengerind.`package` = "com.whatsapp"
        try {
            startActivity(sengerind)
        }catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }
    }
}