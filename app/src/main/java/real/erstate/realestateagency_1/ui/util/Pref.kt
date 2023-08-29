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

    fun isProfile(): Boolean {
        return sharedPref.getBoolean("username", false)
    }

    fun setProfileUser(isSnow: Boolean) {
        sharedPref.edit().putBoolean("username", isSnow).apply()
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

    fun isLogin():String{
        return sharedPref.getString("loginm","").toString()
    }
    fun setLogin(isSnow: String){
        sharedPref.edit().putString("loginm",isSnow).apply()
    }

    fun isPasword():String{
        return sharedPref.getString("paswordf","").toString()
    }

    fun setPasword(isSnow: String){
        sharedPref.edit().putString("paswordf",isSnow).apply()
    }

    fun isToken():String{
        return sharedPref.getString("token","").toString()
    }

    fun setToken(isSnow: String){
        sharedPref.edit().putString("token",isSnow).apply()
    }
}