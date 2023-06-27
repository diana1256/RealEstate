package real.erstate.realestateagency_1.ui.fragment.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.repository.Repository

class AllViewModel (private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun getApartment(): LiveData<Resource<ApartmentListResponse>> {
        return repository.setApartment()
    }
}