package real.erstate.realestateagency_1.data.local



import okhttp3.MultipartBody
import real.erstate.realestateagency_1.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface RealEstateAPI {

           @GET("apartment/")
         suspend fun getApartments(
               @Query("search") title: String,
            ): Response<ApartmentListResponse>


         @GET("apartment/")
         suspend fun search(
             @Query("region")region: String,
             @Query("room_count") room:String
         ):Response<ApartmentListResponse>

        @GET("apartment/")
        suspend fun setApartments(
        ): Response<ApartmentListResponse>

    @GET("apartment/{id}/")
    suspend fun setApartme(
        @Path("id")id : String
    ): Response<Apartment>

    @POST("apartment/")
       suspend fun createApartment(
            @Header("Authorization") token: String,
            @Body data: ApartmentCreate
        ): Response<ApartmentCreate>

        @POST("floor/apartment/")
       suspend fun createFloorApartment(
            @Header("Authorization") token: String,
            @Body data: String
        ): Response<Floor>


        @Multipart
        @POST("image/apartments/")
        suspend fun createImageApartment(
            @Header("Authorization") token: String,
            @Part image : MultipartBody.Part,
            @Part("apartment") apartmentId: Int
        ): Response<ApartmentImage>

        @GET("image/apartments/")
        suspend fun getApartmentImages(
            @Query("limit") limit: Int,
            @Query("offset") offset: String
        ): Response<ImageApartmentListResponse>

        @POST("series/apartment/")
       suspend fun createSeriesApartment(
            @Header("Authorization") token: String,
            @Body series: String
        ): Response<Series>


        @POST("token/login/")
       suspend fun createTokenLogin(
            @Body credentials: TokenObtainPair
        ): Response<LoginResponse>

       @GET("types/apartments/")
       suspend fun getApartmentType():
               Response<ApartmentTypeResponse>

        @GET("users/")
        suspend fun searchUsers(
            @Query("login") query: String
        ): Response<UserResponse>

        @POST("users/")
       suspend fun createUser(
            @Body user: addUser
        ): Response<addUser>

       @POST("ads/")
       suspend fun addAds(
           @Body ads: Ads
       ): Response<AdsAp>

       @GET("banner/")
       suspend fun getBanner():Response<ImageResponse>

       @POST("document/apartment/")
       suspend fun addDocument(
           @Header("Authorization")token: String,
           @Body title: String
       ):Response<DocumentResponse>

       @GET("document/apartment/")
       suspend fun getDocument():Response<DataResponse>

       @GET("floor/apartment/")
       suspend fun getFloor():Response<DataResponse>

       @GET("types/apartments/")
       suspend fun getType():Response<DataResponse>

       @GET("series/apartment/")
       suspend fun getSeries():Response<DataResponse>

       @GET("region/")
       suspend fun getRegion():Response<DataReonse>

       @GET("currency/")
       suspend fun getCurrency():Response<DataResponseList>

       @POST("currency/")
       suspend fun addCurrency(
           @Header("Authorization") token: String,
           @Body symbol:String,name: String
       ):Response<real.erstate.realestateagency_1.data.model.Result>

       @GET("review/")
       suspend fun getRew():Response<RewList>
       @POST("region/")
       suspend fun addRegion(
           @Header("Authorization") token: String,
           @Body name:String
       ):Response<real.erstate.realestateagency_1.data.model.Result>

        @POST("types/apartments/")
        suspend fun addType(
            @Header("Authorization")token: String,
            @Body name: String
        ):Response<real.erstate.realestateagency_1.data.model.Result>
}