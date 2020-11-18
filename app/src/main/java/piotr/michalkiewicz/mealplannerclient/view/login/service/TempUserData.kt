package piotr.michalkiewicz.mealplannerclient.view.login.service

import android.annotation.SuppressLint
import android.provider.Settings
import com.facebook.FacebookSdk.getApplicationContext


class TempUserData {

    companion object {
        @SuppressLint("HardwareIds")
        fun createTempUserName(): String {
            return Settings.Secure.getString(
                getApplicationContext().contentResolver,
                Settings.Secure.ANDROID_ID
            ).toString()
        }
    }
}