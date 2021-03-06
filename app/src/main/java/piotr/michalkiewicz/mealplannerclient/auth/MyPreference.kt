package piotr.michalkiewicz.mealplannerclient.auth

import piotr.michalkiewicz.mealplannerclient.auth.model.Token
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.REFRESH_TOKEN_SHARED_PREF
import piotr.michalkiewicz.mealplannerclient.view.MainActivity.Companion.MY_PREFERENCES

class MyPreference { // toDo  http://blog.udinic.com/2013/04/24/write-your-own-android-authenticator/

    //    private val preference = context.getSharedPreferences("mealTime", Context.MODE_PRIVATE)
    private val preference = MY_PREFERENCES
    private val TOKEM = "TOKEN"

    fun getToken(): String? {
        return preference.getString(TOKEM, "")
    }

    fun getRefreshToken(): String? {
        return preference.getString(REFRESH_TOKEN_SHARED_PREF, "")
    }

    fun setToken(token: Token) {
        val editor = preference.edit()
        editor.putString("TOKEN", token.accessToken)
        editor.putString("REFRESH_TOKEN", token.refreshToken)
        editor.putString("SCOPE", token.scope)
        editor.putString("TOKEN_TYPE", token.tokenType)
        editor.putInt("EXPIRES_IN", token.expiresIn)
        editor.apply()
    }
}