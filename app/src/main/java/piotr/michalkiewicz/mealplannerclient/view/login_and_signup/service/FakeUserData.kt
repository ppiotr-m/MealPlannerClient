package piotr.michalkiewicz.mealplannerclient.view.login_and_signup.service

import android.annotation.SuppressLint
import android.provider.Settings
import com.facebook.FacebookSdk.getApplicationContext


class FakeUserData {

    companion object {
        @SuppressLint("HardwareIds")
        fun createFakeUserName(): String {
           return Settings.Secure.getString(getApplicationContext().contentResolver, Settings.Secure.ANDROID_ID).toString()
        }
    }
}