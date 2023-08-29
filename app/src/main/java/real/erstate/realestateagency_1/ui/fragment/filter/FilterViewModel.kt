package real.erstate.realestateagency_1.ui.fragment.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.model.ApiResponse
import real.erstate.realestateagency_1.data.model.DataReonse
import real.erstate.realestateagency_1.data.model.DataResponse
import real.erstate.realestateagency_1.data.repository.Repository

class FilterViewModel(private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun getApartment(): LiveData<Resource<ApiResponse>> {
        return repository.setApartment()
    }

    fun searchFil(title: String): LiveData<Resource<ApiResponse>> {
        return repository.searFilter(title)
    }

    fun search(region: String,room:String): LiveData<Resource<ApartmentListResponse>>{
        return repository.search(region, room)
    }

    fun getType(): LiveData<Resource<DataResponse>> {
        return repository.getType()
    }

    fun getRegion(): LiveData<Resource<DataReonse>> {
        return repository.getRegion()
    }
}