package real.erstate.realestateagency_1.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.model.ApartmentTypeResponse
import real.erstate.realestateagency_1.data.repository.Repository

class DashboardViewModel (private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun searFilter(title: String): LiveData<Resource<ApartmentListResponse>> {
        return repository.searFilter(title)
    }

    fun search(region:String,room:String): LiveData<Resource<ApartmentListResponse>>{
        return repository.searchSer( region, room)
    }

    fun getApartment(): LiveData<Resource<ApartmentListResponse>> {
        return repository.setApartment()
    }
}