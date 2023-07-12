package real.erstate.realestateagency_1.data.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Favorite(
    val id : String ,
    val status : String ,
    val image: String,
    var tvDil: String,
    var tvTitle: String,
    var tvSan: String,
    var tvKm: String,
    var tvRoom: String,
    var tvLocation: String
): Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}