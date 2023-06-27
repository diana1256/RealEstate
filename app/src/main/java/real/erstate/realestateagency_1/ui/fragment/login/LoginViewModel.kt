package real.erstate.realestateagency_1.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.UserResponse
import real.erstate.realestateagency_1.data.repository.Repository


class LoginViewModel (private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

     fun searchUser(name:String): LiveData<Resource<UserResponse>> {
         return repository.searchUser(name)
     }
}