package piotr.michalkiewicz.mealplannerclient.auth

import android.content.Context
import piotr.michalkiewicz.mealplannerclient.auth.model.Token

class MyPreference(context: Context) { // toDo  http://blog.udinic.com/2013/04/24/write-your-own-android-authenticator/

    private val preference = context.getSharedPreferences("mealTime", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return preference.getString("TOKEN", "")
    }

    fun getRefreshToken(): String? {
        return preference.getString("REFRESH_TOKEN", "")
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