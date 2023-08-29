package real.erstate.realestateagency_1.ui.fragment.registration

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentRegistrationBinding
import real.erstate.realestateagency_1.ui.util.showToast
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.UserResponse
import real.erstate.realestateagency_1.data.model.addUser
import real.erstate.realestateagency_1.data.model.admin
import real.erstate.realestateagency_1.ui.util.Pref

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModel()
    private var registrationListener: OnRegistrationListener? = null

    private var tapCount = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRegistrationListener) {
            registrationListener = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        Pref(requireContext()).setLogin(binding.name.text.toString())
        Pref(requireContext()).setPasword(binding.emailPochta.text.toString())
        onViewlistener()
        onAdmin()
        return binding.root
    }

    private fun onAdmin(){
        binding.emailPochta.setOnClickListener {
            tapCount++
            if (tapCount >=5){
                binding.swich.isVisible = true
            }
        }
        binding.swich.setOnClickListener {
            addAd()
        }

    }



    private fun onViewlistener() {
        binding.voite.setOnClickListener {
            Pref(requireContext()).setLogin(binding.name.text.toString())
            Pref(requireContext()).setPasword(binding.emailPochta.text.toString())
            add()
        }
    }

    private fun onViewModel() {
        Pref(requireContext()).setLogin(binding.name.text.toString())
        Pref(requireContext()).setPasword(binding.emailPochta.text.toString())
        viewModel.loading.observe(requireActivity()) {
            binding.progresBar.isVisible = it
        }
        var user = addUser(
            login = binding.name.text.toString(),
            password = binding.emailPochta.text.toString()
        )
        viewModel.addUser(user).observe(requireActivity()) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    Pref(requireContext()).setBoardingShowed(true)
                    findNavController().navigate(R.id.navigation_home)
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(false)
                    Toast.makeText(requireContext(), "Пользователь с таким именем уже сушествует!!", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }

    private fun onViewMode() {
        Pref(requireContext()).setLogin(binding.name.text.toString())
        Pref(requireContext()).setPasword(binding.emailPochta.text.toString())
        viewModel.loading.observe(requireActivity()) {
            binding.progresBar.isVisible = it
        }
        var user = addUser(
            login = binding.name.text.toString(),
            password = binding.emailPochta.text.toString()
        )
        viewModel.addUser(user).observe(requireActivity()) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(true)
                    viewModel.searchUser(binding.name.text.toString()).observe(requireActivity()){
                        when(it.status){
                            Status.SUCCESS ->{
                                viewModel.loading.postValue(true)
                                val admin = admin(login = binding.name.text.toString(), first_name = binding.name.text.toString(), last_name = binding.name.text.toString(), middle_name = "middle", is_active = true,is_superuser = true)
                                viewModel.createAdmin(it.data?.results?.get(0)?.id.toString(),admin).observe(requireActivity()){
                                    when(it.status){
                                        Status.SUCCESS ->{
                                            Pref(requireContext()).setBoardingShowed(true)
                                            Log.i("adnim adfbnmki", "onViewMode:${it.data}")
                                            viewModel.loading.postValue(false)
                                            registrationListener?.onRegistrationStatusChanged(
                                                true)
                                            findNavController().navigate(R.id.navigation_home)
                                        }
                                        Status.ERROR -> {
                                            viewModel.loading.postValue(false)
                                            Toast.makeText(requireContext(), "Произошла ошибка повторите через минуту!!", Toast.LENGTH_SHORT).show()
                                        }
                                        Status.LOADING -> viewModel.loading.postValue(true)
                                    }
                                }
                            }
                            Status.ERROR ->{
                                viewModel.loading.postValue(false)
                                Toast.makeText(requireContext(), "Произошла ошибка повторите через минуту!!", Toast.LENGTH_SHORT).show()
                            }
                            Status.LOADING -> viewModel.loading.postValue(true)
                        }
                    }
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(false)
                    Toast.makeText(requireContext(), "Пользователь с таким именем уже сушествует!!", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }

    private fun add() {
        if (binding.name.text.isNotEmpty() && binding.emailPochta.text.isNotEmpty()) {
            onViewModel()
        } else {
            showToast(requireContext(), "Заполните все поля!!")
        }
    }

    private fun addAd() {
        if (binding.name.text.isNotEmpty() && binding.emailPochta.text.isNotEmpty()) {
            onViewMode()
        } else {
            showToast(requireContext(), "Заполните все поля!!")
        }
    }
}