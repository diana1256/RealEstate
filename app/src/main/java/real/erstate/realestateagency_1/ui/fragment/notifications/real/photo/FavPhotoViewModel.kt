package real.erstate.realestateagency_1.ui.fragment.notifications.real.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.repository.Repository

class FavPhotoViewModel(private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun  getImage(limit: String): LiveData<Resource<Apartment>> {
        return repository.setIm(limit)
    }
}