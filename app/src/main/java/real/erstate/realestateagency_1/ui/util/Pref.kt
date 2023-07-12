package real.erstate.realestateagency_1.ui.util

import android.content.Context
import android.content.SharedPreferences

class Pref ( context: Context) {
    val sharedPref: SharedPreferences =
        context.getSharedPreferences("presences", Context.MODE_PRIVATE)

    fun isBoardingShowed(): Boolean {
        return sharedPref.getBoolean("board", false)
    }

    fun setBoardingShowed(isSnow: Boolean) {
        sharedPref.edit().putBoolean("board", isSnow).apply()
    }

    fun isProfile(): String {
        return sharedPref.getString("username", "").toString()
    }

    fun setProfileUser(isSnow: String) {
        sharedPref.edit().putString("username", isSnow).apply()
    }

    fun isSena(): String {
        return sharedPref.getString("sena", "").toString()
    }

    fun setSena(isSnow: String) {
        sharedPref.edit().putString("sena", isSnow).apply()
    }

    fun isType(): String {
        return sharedPref.getString("type", "").toString()
    }

    fun setType(isSnow: String) {
        sharedPref.edit().putString("type", isSnow).apply()
    }

    fun isKm(): String {
        return sharedPref.getString("km", "").toString()
    }

    fun setKm(isSnow: String) {
        sharedPref.edit().putString("km", isSnow).apply()
    }

    fun isAdress(): String {
        return sharedPref.getString("adress", "").toString()
    }

    fun setAdress(isSnow: String) {
        sharedPref.edit().putString("adress", isSnow).apply()
    }

    fun isAdressId(): String {
        return sharedPref.getString("adre", "").toString()
    }

    fun setAdressId(isSnow: String) {
        sharedPref.edit().putString("adre", isSnow).apply()
    }

    fun isEtak(): String {
        return sharedPref.getString("etak", "").toString()
    }

    fun setEtak(isSnow: String) {
        sharedPref.edit().putString("etak", isSnow).apply()
    }

    fun isRoomCount(): String {
        return sharedPref.getString("count", "").toString()
    }

    fun setRoomCount(isSnow: String) {
        sharedPref.edit().putString("count", isSnow).apply()
    }

}