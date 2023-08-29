package real.erstate.realestateagency_1.ui.fragment.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.UserResponse
import real.erstate.realestateagency_1.data.model.addUser
import real.erstate.realestateagency_1.data.model.admin
import real.erstate.realestateagency_1.data.repository.Repository


class RegistrationViewModel (private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun addUser(data: addUser): LiveData<Resource<UserResponse>> {
        return repository.addUser(data)
    }

    fun searchUser(name:String): LiveData<Resource<UserResponse>>{
        return repository.searchUser(name)
    }

    fun createAdmin(id:String,admin: admin): LiveData<Resource<admin>>{
        return repository.createAdmin(id,admin)
    }
}