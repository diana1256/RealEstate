package real.erstate.realestateagency_1.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.model.DataReonse
import real.erstate.realestateagency_1.data.repository.Repository

class HomeViewModel (private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun getApartment(): LiveData<Resource<ApartmentListResponse>> {
        return repository.setApartment()
    }


    fun search(region: String,room:String): LiveData<Resource<ApartmentListResponse>>{
        return repository.search( region, room)
    }

    fun getRegion(): LiveData<Resource<DataReonse>> {
        return repository.getRegion()
    }
}