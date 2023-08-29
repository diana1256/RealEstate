package real.erstate.realestateagency_1.ui.fragment.add

import YourViewPagerAdapter
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.ui.util.setupRecyclerViewOnFocus
import real.erstate.realestateagency_1.ui.util.showToast
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.ApartmentCreate
import real.erstate.realestateagency_1.data.model.TokenObtainPair
import real.erstate.realestateagency_1.databinding.FragmentAddBinding
import real.erstate.realestateagency_1.ui.util.Pref
import java.io.File
import java.io.FileOutputStream


class AddFragment : Fragment() {
    private lateinit var tokenObtainPair: TokenObtainPair
    private lateinit var binding: FragmentAddBinding
    private lateinit var apartmentCreate: ApartmentCreate
    var token = ""
    private val MAX_PHOTOS = 24
    private var selectedPhotos = mutableListOf<Uri>()
    private lateinit var selectedImageUri: Uri
    private val adapter = YourViewPagerAdapter()
    private val viewModel: AddViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onClik()
        onVi()
        edit()
        binding.etIdf.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFio.setText(Pref(requireContext()).isLogin())
        binding.etIdg.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFty.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFioDel.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFtyDel.setText(Pref(requireContext()).isPasword())
        return binding.root
    }


    private fun onClik() {
        binding.tvb.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            intent.putExtra(Intent.ACTION_PICK, true)
            activityResultLauncher.launch(intent)
        }
        binding.btn.setOnClickListener {
            onViewModel()
        }
        binding.btnHjko.setOnClickListener {
            onViewModel()
        }

        binding.btnDel.setOnClickListener {
            onViewModel()
        }
        binding.btnKkk.setOnClickListener {
            add()
        }
        binding.btnKkkDel.setOnClickListener {
            deleteApartment()
        }
        binding.btnPk.setOnClickListener {
            upload()
        }
    }

    private fun onViewModel() {
        tokenObtainPair = TokenObtainPair(
            login = binding.etLayoutFio.text.toString(),
            password = binding.etLayoutFty.text.toString()
        )
        viewModel.addTokenLogin(tokenObtainPair)
            .observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        token = it.data?.access.toString()
                        Log.i("lkjiuhyg", "onViewModel:$token")
                    }

                    Status.ERROR -> {
                        Log.i("eror", "onViewModel:${it.message}")
                    }

                    Status.LOADING -> viewModel.loading.postValue(true)
                }
            }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    if (selectedPhotos.size < MAX_PHOTOS) {
                        binding.vpImage.adapter = adapter
                        selectedPhotos.add(uri)
                        adapter.addTask(uri)
                        selectedImageUri = uri
                        binding.dfgh.isVisible = uri.toString().isEmpty()
                        Log.i("lokij", ":$selectedPhotos")
                    } else {
                        showToast(requireContext(), "Вы достигли предела $MAX_PHOTOS")
                    }
                }
            }
        }

    private fun upload() {
        val fileDir = requireContext().filesDir
        val file = File(fileDir, "image.png")
        val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image", file.name, requestBody)
        if (token.isEmpty() && part.toString().isEmpty() && binding.etId.text.toString().isEmpty()){
            showToast(requireContext(),"Заполните все поля")
        } else {
            viewModel.setImage("Bearer $token", part, binding.etId.text.toString().toInt())
                .observe(requireActivity()) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Log.i("xscd", "upload:${it.data}")
                            showToast(requireContext(), "Данные отправлены!!")
                        }

                        Status.ERROR -> {
                            showToast(requireContext(), "Произошла ошибка повторите через минуту")
                        }

                        Status.LOADING -> {}
                    }
                }
        }
    }

    private fun deleteApartment(){
        Log.i("asdfqwer", "deleteApartment:${token}")
        viewModel.deleteApartment("Bearer $token",binding.etLayoutFoDel.text.toString().toInt()).observe(requireActivity()){
            when(it.status){
                Status.SUCCESS->{
                    Toast.makeText(requireContext(), "Apartment ${binding.etLayoutFoDel.text.toString()} был удалён!!", Toast.LENGTH_SHORT).show()
                    Log.i("delete", "deleteApartment:${it.data}")
                }
                Status.ERROR ->{}
                Status.LOADING->{}
            }
        }
    }

    fun add() {
        Log.i("ijuhy", "add:${binding.etL.isChecked}")
        apartmentCreate = ApartmentCreate(
            title = binding.etText.text.toString(),
            square = binding.etKm.text.toString(),
            communications = binding.etDf.text.toString(),
            description = binding.editIn.text.toString(),
            address = binding.etLocation.text.toString(),
            best = binding.etL.isChecked,
            price = binding.etSan.text.toString(),
            room_count = binding.etRoom.text.toString(),
            lat = binding.etPhone.text.toString(),
            lng = "0.900000",
            currency = binding.etEr.text.toString().toInt(),
            author = 1,
            document = binding.etDvX.text.toString().toInt(),
            type = binding.etDil.text.toString().toInt(),
            floor = binding.etRoom.text.toString().toInt(),
            series = binding.etDvZxx.text.toString().toInt(),
            region = binding.etDav.text.toString().toInt(),
            plot_weaving = "q w"
        )
        if (token.isEmpty() && binding.etDvX.text.toString().isEmpty() && binding.etDil.text.toString().isEmpty()  && binding.etRoom.text.toString().isEmpty() && binding.etDvZxx.text.toString().isEmpty() && binding.etDav.text.toString().isEmpty() && binding.etEr.text.toString().isEmpty() && binding.etText.text.toString().isEmpty() && binding.etKm.text.toString().isEmpty() && binding.etDf.text.toString().isEmpty() && binding.editIn.text.toString().isEmpty() && binding.etLocation.text.toString().isEmpty() && binding.etL.isChecked.toString().isEmpty() && binding.etSan.text.toString().isEmpty() && binding.etRoom.text.toString().isEmpty() && binding.etPhone.text.toString().isEmpty()){
            showToast(requireContext(),"Заполните все поля!!")
        } else {
            viewModel.addApartment("Bearer $token", apartmentCreate)
                .observe(requireActivity()) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Log.i("axsdcfv", "add:$token")
                            Log.i("hbj", "onViewModel:$it")
                            showToast(requireContext(), "Данные отправлены!!")
                        }

                        Status.ERROR -> {
                            Log.i("olp", "onViewModel:${it.message}")
                        }

                        Status.LOADING -> viewModel.loading.postValue(true)
                    }
                }
        }
    }


    fun onVi() {
        viewModel.getFloor().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvRoom.adapter = AddSer(it, this::onClick)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }

        viewModel.getCyrency().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvValute.adapter = AddCyrrency(it, this::onClickCy)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }

        viewModel.getSeries().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvSeries.adapter = AddSer(it, this::onClickS)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }

        viewModel.getDocument().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvDocum.adapter = AddSer(it, this::onClickD)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }

        viewModel.getRegion().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvRegifn.adapter = AdapterAdd(it, this::onClickF)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }

        viewModel.getType().observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvType.adapter = AddSer(it, this::onClickT)
                }

                Status.ERROR -> {
                    Log.i("olp", "onViewModel:${it.message}")
                }

                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }


    fun edit() {
        binding.etDil.setupRecyclerViewOnFocus(binding.rvType)
        binding.etRoom.setupRecyclerViewOnFocus(binding.rvRoom)
        binding.etEr.setupRecyclerViewOnFocus(binding.rvValute)
        binding.etDvZxx.setupRecyclerViewOnFocus(binding.rvSeries)
        binding.etDvX.setupRecyclerViewOnFocus(binding.rvDocum)
        binding.etDav.setupRecyclerViewOnFocus(binding.rvRegifn)
    }

    private fun onClick(title: String) {
        binding.etRoom.setText(title)
        binding.rvRoom.isVisible = false
    }

    private fun onClickD(title: String) {
        binding.etDvX.setText(title)
        binding.rvDocum.isVisible = false
    }

    private fun onClickF(title: String) {
        binding.etDav.setText(title)
        binding.rvRegifn.isVisible = false
    }

    private fun onClickT(title: String) {
        binding.etDil.setText(title)
        binding.rvType.isVisible = false
    }

    private fun onClickS(title: String) {
        binding.etDvZxx.setText(title)
        binding.rvSeries.isVisible = false
    }

    private fun onClickCy(title: String) {
        binding.etEr.setText(title)
        binding.rvValute.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        binding.etIdf.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFio.setText(Pref(requireContext()).isLogin())
        binding.etIdg.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFty.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFioDel.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFtyDel.setText(Pref(requireContext()).isPasword())
    }

    override fun onPause() {
        super.onPause()
        binding.etIdf.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFio.setText(Pref(requireContext()).isLogin())
        binding.etIdg.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFty.setText(Pref(requireContext()).isPasword())
        binding.etLayoutFioDel.setText(Pref(requireContext()).isLogin())
        binding.etLayoutFtyDel.setText(Pref(requireContext()).isPasword())
    }
}