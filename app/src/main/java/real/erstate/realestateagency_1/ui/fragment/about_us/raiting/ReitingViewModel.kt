package real.erstate.realestateagency_1.ui.fragment.about_us.raiting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.RewList
import real.erstate.realestateagency_1.data.repository.Repository

class ReitingViewModel(private val repository: Repository): ViewModel() {

    val loading = MutableLiveData<Boolean>()

    fun getRew(): LiveData<Resource<RewList>> {
        return repository.getRew()
    }
}