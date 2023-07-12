package real.erstate.realestateagency_1.ui.fragment.registration

import android.content.pm.ActivityInfo
import android.os.Bundle
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
import real.erstate.realestateagency_1.data.local.result.Status
import real.erstate.realestateagency_1.data.model.UserResponse
import real.erstate.realestateagency_1.data.model.addUser

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
        var user: addUser = addUser(
            login = binding.name.text.toString(),
            password = binding.emailPochta.text.toString()
        )
        viewModel.addUser(user).observe(requireActivity()) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    viewModel.loading.postValue(false)
                    response.data?.let { add(it) }

                }
                Status.ERROR -> {
                    viewModel.loading.postValue(true)
                }
                Status.LOADING -> viewModel.loading.postValue(true)
            }
        }
    }

    private fun add(login: UserResponse) {
        val tyu = login.results
         if (tyu.isEmpty()){
            showToast(requireContext(),"Пользователь не найдайден!!")
        }else{
            findNavController().navigate(R.id.navigation_home)
        }
    }
}