package real.erstate.realestateagency_1.ui.fragment.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*
import real.erstate.realestateagency_1.data.repository.Repository


class AddViewModel (private val repository: Repository):ViewModel() {

    val loading = MutableLiveData<Boolean>()

      fun  addApartment(token:String,data: ApartmentCreate) : LiveData<Resource<ApartmentCreate>>{
        return repository.addApartment(token,data)
    }


      fun setImage(token: String, imageFile: MultipartBody.Part, apartmentId: Int): LiveData<Resource<ApartmentImage>>{
        return repository.setImage(token, imageFile, apartmentId)
    }


    fun getSeries():LiveData<Resource<DataResponse>>{
        return repository.getSeries()
    }

    fun getCyrency():LiveData<Resource<DataResponseList>>{
        return repository.getCurrency()
    }


    fun getType(): LiveData<Resource<DataResponse>> {
        return repository.getType()
    }

    fun getRegion(): LiveData<Resource<DataReonse>> {
        return repository.getRegion()
    }

    fun getDocument(): LiveData<Resource<DataResponse>> {
        return repository.getDocument()
    }

    fun addDocument(token:String,name:String): LiveData<Resource<DocumentResponse>> {
        return repository.addDocument(token, name)
    }




    fun addSeries(token:String,name:String): LiveData<Resource<Series>> {
        return repository.addSeries(token,name)
    }

    fun getFloor(): LiveData<Resource<DataResponse>> {
        return repository.getFloor()
    }

    fun addTokenLogin(data: TokenObtainPair) : LiveData<Resource<LoginResponse>> {
        return repository.addTokenLogin(data)
    }

    fun deleteApartment(token: String,id:Int): LiveData<Resource<Unit>>{
        return repository.deleteApartment(token,id)
    }
}