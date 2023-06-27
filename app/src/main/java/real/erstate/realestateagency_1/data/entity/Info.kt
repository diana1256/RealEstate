package real.erstate.realestateagency_1.data.entity

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Info (
    val region:String,
    val km : String,
    val room : String,
    val sena: String,
    val h:String
        ): Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}