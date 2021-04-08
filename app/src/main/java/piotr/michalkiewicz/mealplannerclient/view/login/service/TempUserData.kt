package piotr.michalkiewicz.mealplannerclient.view.login.service

import android.annotation.SuppressLint
import android.provider.Settings
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class TempUserData {

    companion object {
        @SuppressLint("HardwareIds")
        fun createTempUserName(): String {
            return Settings.Secure.getString(
                MainActivity.getMainContext().contentResolver,
                Settings.Secure.ANDROID_ID
            ).toString()
        }
    }
}