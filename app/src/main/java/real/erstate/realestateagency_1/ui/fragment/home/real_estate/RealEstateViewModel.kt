package real.erstate.realestateagency_1.ui.fragment.home.real_estate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.Apartment
import real.erstate.realestateagency_1.data.model.ApartmentListResponse
import real.erstate.realestateagency_1.data.repository.Repository

class RealEstateViewModel (private val repository: Repository): ViewModel(){


    val loading = MutableLiveData<Boolean>()

    fun getApartment(id:String): LiveData<Resource<Apartment>> {
        return repository.setIm(id)
    }
    fun  getImage(limit: String): LiveData<Resource<Apartment>> {
        return repository.setIm(limit)
    }
}