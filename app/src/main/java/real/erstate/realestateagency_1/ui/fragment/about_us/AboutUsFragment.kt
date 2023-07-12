package real.erstate.realestateagency_1.ui.fragment.about_us

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentAboutUsBinding
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.Ads

@Suppress("DEPRECATION")
class AboutUsFragment : Fragment() {
    private lateinit var binding : FragmentAboutUsBinding
    val viewModel: AboutUsViewModel by viewModel()
    var gt = ""
    var cdvf = ""
    private val listLoad = arrayListOf<ModelUs>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentAboutUsBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        listLoad.add(ModelUs("",R.drawable.screensaver,""))
        listLoad.add(ModelUs("",R.drawable.screensaver,""))
        listLoad.add(ModelUs("",R.drawable.screensaver,""))
        listLoad.add(ModelUs("",R.drawable.screensaver,""))
        listLoad.add(ModelUs("",R.drawable.screensaver,""))
        val  adapterLoad = AdapterLoad()
        binding.con.visibility =View.INVISIBLE
        binding.shimmer.startShimmer()

        adapterLoad.submitList(listLoad)
        binding.load.rvRecyc.adapter = adapterLoad
        inttView()
        DialogCustom()
        initClickListener()
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun initClickListener() {
        binding.item.wertt.setOnClickListener {
            findNavController().navigate(R.id.reitingFragment)
        }
        binding.item.cardBtn.setOnClickListener{
            openLinkInChrome("https://nedvijimost.kg/")
        }
        binding.item.cardIv.setOnClickListener {
            val phoneNumber = "996550900700"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phoneNumber")
            startActivity(intent)
        }
        binding.item.cardF.setOnClickListener {
            openInstagramAccount("number.one.kg/")
        }
        binding.item.cardOlo.setOnClickListener {
            openTikTokAccount("@nedviijimost.kg")
        }
        binding.item.cardOpo.setOnClickListener {
            makePhoneCall("0550900700")
        }

        binding.item.sain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://nedvijimost.kg/")
            startActivity(intent)
        }
    }

    fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }
    fun openTikTokAccount(username: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val url = "https://www.tiktok.com/$username"
        val uri = Uri.parse(url)
        intent.data = uri

        if (isTikTokInstalled()) {
            intent.setPackage("com.zhiliaoapp.musically")
        }

        startActivity(intent)
    }

    fun isTikTokInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        try {
            packageManager.getPackageInfo("com.zhiliaoapp.musically", PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
    }

    fun openInstagramAccount(username: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        val url = "https://www.instagram.com/$username"
        val uri = Uri.parse(url)
        intent.data = uri

        if (isInstagramInstalled()) {
            intent.setPackage("number.one.kg/")
        }

        startActivity(intent)
    }

    private fun isInstagramInstalled(): Boolean {
        val packageManager = requireActivity().packageManager
        return try {
            packageManager.getPackageInfo("com.instagram.android", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    private fun inttView() {
        viewModel.loading.observe(requireActivity()) {
            binding.shimmer.isVisible = it
        }
        viewModel.getRew().observe(requireActivity()){
            when(it.status){
                Status.SUCCESS->{
                    viewModel.loading.postValue(false)
                    binding.con.visibility =View.VISIBLE
                  val   asd = Adapter(it)
                    binding.item.rvRecyc.adapter = asd
                       Log.i("kjnh", "inttView:${it.data}")
                }
                Status.LOADING->{
                    viewModel.loading.postValue(true)
                }
                Status.ERROR->{
                    viewModel.loading.postValue(true)
                }
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun DialogCustom() {
        binding.item.btn.setOnClickListener {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.diolog_about_us, null)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(true)
            val mnh = view.findViewById<EditText>(R.id.mnh)
            gt = mnh.text.toString()
            val name = view.findViewById<EditText>(R.id.et_name)
            cdvf = name.text.toString()
            val btn = view.findViewById<MaterialButton>(R.id.btn_)
            btn.setOnClickListener {
                onWEr(name.text.toString(),mnh.text.toString())
                dialog.dismiss()

            }


        }
    }

    fun openLinkInChrome(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("com.android.chrome") // Указываем пакет браузера Chrome

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Обработка случая, когда Chrome не установлен
            // или недоступен на устройстве
            // Можно вывести сообщение об ошибке или выполнить другие действия
        }
    }

    fun onWEr(string: String,stri: String){
        val data = Ads(string, stri)
        viewModel.addAds(data).observe(requireActivity()){
            when(it.status) {
                Status.LOADING ->{
                }
                Status.SUCCESS -> {}
                Status.ERROR -> {}
            }
        }
    }
}