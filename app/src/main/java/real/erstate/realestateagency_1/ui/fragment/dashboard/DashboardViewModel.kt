package real.erstate.realestateagency_1.ui.fragment.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*
import real.erstate.realestateagency_1.data.repository.Repository

class DashboardViewModel (private val repository: Repository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun searFilter(title: String): LiveData<Resource<ApiResponse>> {
        return repository.searFilter(title)
    }

    fun searchUser(name:String): LiveData<Resource<UserResponse>> {
        return repository.searchUser(name)
    }

    fun setFavorite(token: String,favorite: Favorite): LiveData<Resource<FavoriteResurce>>{
        return repository.serFavorite(token,favorite)
    }

    fun search(region:String,room:String): LiveData<Resource<ApiResponse>> {
        return repository.searchSer( region, room)
    }

    fun getApartment(): LiveData<Resource<ApiResponse>> {
        return repository.setApartment()
    }
}