package real.erstate.realestateagency_1.ui.fragment.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*
import real.erstate.realestateagency_1.data.repository.Repository

class NotificationsViewModel(private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun getApartment(token:String): LiveData<Resource<Response>> {
        return repository.getFavorite(token)
    }

    fun delete(data: String,id:String): LiveData<Resource<Unit>> {
        return repository.deleteFav(data,id)
    }

}