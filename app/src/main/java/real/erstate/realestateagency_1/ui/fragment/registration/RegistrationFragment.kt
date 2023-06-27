package real.erstate.realestateagency_1.ui.fragment.registration

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentRegistrationBinding
import real.erstate.realestateagency_1.ui.util.showToast
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.addUser
import real.estate.realestateagency1.ui.util.Pref

class RegistrationFragment : Fragment() {
    private lateinit var binding : FragmentRegistrationBinding
    private val viewModel : RegistrationViewModel by viewModel ()
    private lateinit var user: addUser
    private var login = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater,container,false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onViewlistener()
        return binding.root
    }

    private fun onViewlistener() {
        binding.voite.setOnClickListener {
            onViewModel()
        }
    }

    private fun onViewModel() {
        viewModel.loading.observe(requireActivity()) {
            binding.progresBar.isVisible = it
        }
        user = addUser(login = binding.name.text.toString(),password = binding.emailPochta.text.toString())
        viewModel.addUser(user).observe(requireActivity()) {
            when (it.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    add(it)
                    login = it.data?.login!!
                }
                Status.ERROR -> {
                    viewModel.loading.postValue(true)
                    Log.i("olio", "initViewModel:${it.message}")
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }

    private fun add(data: Resource<addUser>) {
        if (data.data != null) {
            user.login = data.data.login
            user.login = data.data.login
            Log.i("oko", "add:${data.data} ")
            val pref = Pref(requireContext())
            pref.setBoardingShowed(true)
            findNavController().navigate(R.id.navigation_home)
        } else {
            showToast(requireContext(), "Заполните все поля!")
        }
    }
}