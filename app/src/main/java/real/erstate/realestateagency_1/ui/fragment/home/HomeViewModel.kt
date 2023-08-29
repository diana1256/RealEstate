package real.erstate.realestateagency_1.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*
import real.erstate.realestateagency_1.data.repository.Repository

class HomeViewModel (private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun searchUser(name:String): LiveData<Resource<UserResponse>> {
        return repository.searchUser(name)
    }

    fun getApartment(): LiveData<Resource<ApiResponse>> {
        return repository.setApartment()
    }

    fun search(region:String,room:String): LiveData<Resource<ApiResponse>> {
        return repository.searchFil( region, room)
    }

    fun getRegion(): LiveData<Resource<DataReonse>> {
        return repository.getRegion()
    }

    fun token(data:TokenObtainPair):LiveData<Resource<LoginResponse>>{
        return repository.addTokenLogin(data)
    }
    fun setFavorite(token: String,favorite: Favorite): LiveData<Resource<FavoriteResurce>>{
        return repository.serFavorite(token,favorite)
    }
}