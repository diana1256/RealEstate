package real.erstate.realestateagency_1.ui.fragment.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.addUser
import real.erstate.realestateagency_1.data.repository.Repository


class RegistrationViewModel (private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun addUser(data: addUser): LiveData<Resource<addUser>>{
        return repository.addUser(data)
    }

}