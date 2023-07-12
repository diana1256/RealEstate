package real.erstate.realestateagency_1.ui.fragment.login

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.fragment.findNavController
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.FragmentLoginBinding
import real.erstate.realestateagency_1.ui.fragment.registration.OnRegistrationListener
import real.erstate.realestateagency_1.ui.util.showToast
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.ui.util.Pref

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var registrationListener: OnRegistrationListener? = null
    private val viewModel: LoginViewModel by viewModel()

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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
        return binding.root
    }

    private fun initView() {
        binding.voite.setOnClickListener {
            onLoginClicked()
        }
        binding.newAcoynt.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    private fun onLoginClicked() {
        val email = binding.email.text.toString()
        if (email.isNotEmpty()) {
            searchUser(email)
            val pref = Pref(requireContext())
            pref.setBoardingShowed(true)
            findNavController().navigate(R.id.navigation_home)
        } else {
            showToast(requireContext(), "Введите мя пользователя")
        }
    }

    private fun searchUser(email: String) {
        viewModel.loading.observe(requireActivity()){
            binding.progresBar.isVisible = it
        }
        viewModel.searchUser(email).observe(requireActivity()) {
            when(it.status) {
                Status.SUCCESS ->{
                    viewModel.loading.postValue(false)
                    registrationListener?.onRegistrationStatusChanged(binding.email.text.toString())
                }
                Status.ERROR ->{
                    viewModel.loading.postValue(true)
                }
                Status.LOADING ->{
                    viewModel.loading.postValue(false)
                }
            }
        }
    }
}