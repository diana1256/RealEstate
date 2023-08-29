package real.erstate.realestateagency_1.data.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import real.erstate.realestateagency_1.data.local.result.Resource
import real.erstate.realestateagency_1.data.model.*


class Repository (private val remoteDataSource: RemoteDataSource) {

    fun searFilter(title: String) = liveData(Dispatchers.IO){
        emit(Resource.loading())
       val filter =remoteDataSource.searFilter(title)
        emit(filter)
    }

    fun deleteFav(token: String,id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val we = remoteDataSource.delete(token,id)
        emit(we)
    }

    fun getRew()= liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val rew = remoteDataSource.getRew()
        emit(rew)
    }
    fun setIm(id:String)= liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val inf = remoteDataSource.setImage(id)
        emit(inf)
    }

    fun serFavorite(token: String,moe:Favorite) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val wer = remoteDataSource.setFavorite(token,moe)
        emit(wer)
    }
    fun  searchUser(name:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val searchUser = remoteDataSource.searchUser(name)
        emit(searchUser)
    }

    fun  getImage(limit: Int, idApartment: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val getImage = remoteDataSource.getImage(limit,idApartment)
        emit(getImage)
    }

    fun getApartmentType() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val getApartmentType = remoteDataSource.getApartmentType()
        emit(getApartmentType)
    }

    fun setApartment() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val apartment = remoteDataSource.setApartment()
        emit(apartment)
    }

    fun addApartment(token:String,data: ApartmentCreate) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addApartment = remoteDataSource.addApartment(token,data)
        emit(addApartment)
    }

    fun  setFloor(token: String,data: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val floor = remoteDataSource.setFloor(token,data)
        emit(floor)
    }

    fun setImage(token:String, imageFile: MultipartBody.Part, apartmentId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val image = remoteDataSource.setImage(token,imageFile,apartmentId)
        emit(image)
    }

    fun addTokenLogin(data: TokenObtainPair) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addTokenLogin = remoteDataSource.addTokenLogin(data)
        emit(addTokenLogin)
    }

    fun addUser(data: addUser) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addUser = remoteDataSource.addUser(data)
        emit(addUser)
    }

    fun addAds(data: Ads) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addAds = remoteDataSource.addAds(data)
        emit(addAds)
    }

    fun getFavorite(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val fav = remoteDataSource.getFavorite(token)
        emit(fav)
    }

    fun getIdApartment(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val idf = remoteDataSource.getIdApartment(id)
        emit(idf)
    }
    fun getType() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val type = remoteDataSource.getType()
        emit(type)
    }

    fun getRegion() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val region = remoteDataSource.getRegion()
        emit(region)
    }

    fun getSeries() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val series = remoteDataSource.getSeries()
        emit(series)
    }

    fun getFloor() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val floor = remoteDataSource.getFloor()
        emit(floor)
    }

    fun getDocument() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val document = remoteDataSource.getDocument()
        emit(document)
    }

    fun addDocument(token: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addDocument = remoteDataSource.addDocument(token, name)
        emit(addDocument)
    }

    fun addRegion(token: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addRegion = remoteDataSource.addRegion(token, name)
        emit(addRegion)
    }

    fun addSeries(token: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addSeries = remoteDataSource.addSeries(token, name)
        emit(addSeries)
    }

    fun addType(token: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val addType = remoteDataSource.addType(token, name)
        emit(addType)
    }

    fun addCurrency(token: String,symbol:String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val cyrrency = remoteDataSource.addCurrency(token,symbol, name)
        emit(cyrrency)
    }

    fun getCurrency() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val cyrrency = remoteDataSource.getCurrency()
        emit(cyrrency)
    }

    fun search(region: String,room:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val sear = remoteDataSource.search( region, room)
        emit(sear)
    }

    fun searchSer(region: String,room:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val sear = remoteDataSource.searchSer( region, room)
        emit(sear)
    }


    fun searchFil(region: String,room:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val sear = remoteDataSource.searchFil( region, room)
        emit(sear)
    }

    fun createAdmin(id: String,admin: admin) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val admin = remoteDataSource.createAdmin(id,admin)
        emit(admin)
    }

    fun deleteApartment(token: String,id: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val delete = remoteDataSource.deleteApartment(token,id)
        emit(delete)
    }
}