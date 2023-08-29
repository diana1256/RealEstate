package real.erstate.realestateagency_1.ui.fragment.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*
import real.erstate.realestateagency_1.data.repository.Repository

class AllViewModel (private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun searchUser(name:String): LiveData<Resource<UserResponse>> {
        return repository.searchUser(name)
    }

    fun setFavorite(token: String,favorite: Favorite): LiveData<Resource<FavoriteResurce>>{
        return repository.serFavorite(token,favorite)
    }

    fun getApartment(): LiveData<Resource<ApiResponse>> {
        return repository.setApartment()
    }
}