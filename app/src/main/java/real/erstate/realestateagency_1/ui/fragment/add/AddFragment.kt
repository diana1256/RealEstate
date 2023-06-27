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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentAddBinding
import real.erstate.realestateagency_1.ui.util.setupRecyclerViewOnFocus
import real.erstate.realestateagency_1.ui.util.showToast
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.ApartmentCreate
import real.erstate.realestateagency_1.data.model.TokenObtainPair
import java.io.File
import java.io.FileOutputStream


class AddFragment : Fragment() {
    private lateinit var tokenObtainPair: TokenObtainPair
    private lateinit var binding: FragmentAddBinding
    private lateinit var apartmentCreate: ApartmentCreate
    var token = ""
    private var isButtonClicked = false
    private val MAX_PHOTOS = 25
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
        addUpload()
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
        binding.btnKkk.setOnClickListener {
            add()
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
                        Log.i("lokij", ":$uri")
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
        viewModel.setImage("Bearer $token", part, binding.etId.text.toString().toInt())
            .observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.i("xscd", "upload:${it.data}")
                    }

                    Status.ERROR -> {
                        showToast(requireContext(), "Произошла ошибка повторите через минуту")
                    }

                    Status.LOADING -> {}
                }
            }
    }

    fun addUpload() {
        binding.etRoom.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etRoom.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etRoom.setTextColor(defaultColor)
                viewModel.setFloor("Bearer $token", binding.etRoom.text.toString())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("xscd", "upload:${it.data}")
                            }

                            Status.ERROR -> {
                                showToast(
                                    requireContext(),
                                    "Произошла ошибка повторите через минуту"
                                )
                            }

                            Status.LOADING -> {}
                        }
                    }
            } else {
                binding.etRoom.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etRoom.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }
        binding.etVer.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etVer.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etVer.setTextColor(defaultColor)
            } else {
                binding.etVer.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etVer.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }
        binding.etEr.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etEr.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etEr.setTextColor(defaultColor)
                viewModel.addCyrency(
                    "Bearer $token",
                    binding.etEr.text.toString(),
                    binding.etVer.text.toString()
                ).observe(requireActivity()) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            Log.i("xscd", "upload:${it.data}")
                        }

                        Status.ERROR -> {
                            showToast(
                                requireContext(),
                                "Произошла ошибка повторите через минуту"
                            )
                        }

                        Status.LOADING -> {}
                    }
                }

            } else {
                binding.etEr.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etEr.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }
        binding.etDvX.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etDvX.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etDvX.setTextColor(defaultColor)
                viewModel.addDocument("Bearer $token", binding.etDvX.text.toString())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("xscd", "upload:${it.data}")
                            }

                            Status.ERROR -> {
                                showToast(
                                    requireContext(),
                                    "Произошла ошибка повторите через минуту"
                                )
                            }

                            Status.LOADING -> {}
                        }
                    }
            } else {
                binding.etDvX.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etDvX.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }

        binding.etDil.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etDil.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etDil.setTextColor(defaultColor)
                viewModel.addType("Bearer $token", binding.etDil.text.toString())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("xscd", "upload:${it.data}")
                            }

                            Status.ERROR -> {
                                showToast(
                                    requireContext(),
                                    "Произошла ошибка повторите через минуту"
                                )
                            }

                            Status.LOADING -> {}
                        }
                    }
            } else {
                binding.etDil.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etDil.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }

        binding.etDvZxx.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etDvZxx.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etDvZxx.setTextColor(defaultColor)
                viewModel.addSeries("Bearer $token", binding.etDvX.text.toString())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("xscd", "upload:${it.data}")
                            }

                            Status.ERROR -> {
                                showToast(
                                    requireContext(),
                                    "Произошла ошибка повторите через минуту"
                                )
                            }

                            Status.LOADING -> {}
                        }
                    }
            } else {
                binding.etDvZxx.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etDvZxx.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }

        binding.etDav.setOnLongClickListener {
            isButtonClicked = !isButtonClicked
            if (isButtonClicked) {
                binding.etDav.setBackgroundResource(R.drawable.bg_lyue)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.card)
                binding.etDav.setTextColor(defaultColor)
                viewModel.addRegion("Bearer $token", binding.etDvX.text.toString())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                Log.i("xscd", "upload:${it.data}")
                            }

                            Status.ERROR -> {
                                showToast(
                                    requireContext(),
                                    "Произошла ошибка повторите через минуту"
                                )
                            }

                            Status.LOADING -> {}
                        }
                    }
            } else {
                binding.etDav.setBackgroundResource(R.drawable.bg_black_tv_line)
                val defaultColor = ContextCompat.getColor(requireContext(), R.color.black)
                binding.etDav.setTextColor(defaultColor)
            }
            return@setOnLongClickListener true
        }
    }

    fun add() {

        apartmentCreate = ApartmentCreate(
            title = binding.etText.text.toString(),
            square = binding.etKm.text.toString(),
            communications = binding.etDf.text.toString(),
            description = binding.editIn.text.toString(),
            address = binding.etLocation.text.toString(),
            best = true,
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
            region = binding.etDav.text.toString().toInt()
        )

        viewModel.addApartment("Bearer $token", apartmentCreate)
            .observe(requireActivity()) {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.i("axsdcfv", "add:$token")
                        Log.i("hbj", "onViewModel:$it")
                    }

                    Status.ERROR -> {
                        Log.i("olp", "onViewModel:${it.message}")
                    }

                    Status.LOADING -> viewModel.loading.postValue(true)
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
    }

    private fun onClickD(title: String) {
        binding.etDvX.setText(title)
    }

    private fun onClickF(title: String) {
        binding.etDav.setText(title)
    }

    private fun onClickT(title: String) {
        binding.etDil.setText(title)
    }

    private fun onClickS(title: String) {
        binding.etDvZxx.setText(title)
    }

    private fun onClickCy(title: String) {
        binding.etEr.setText(title)
    }
}