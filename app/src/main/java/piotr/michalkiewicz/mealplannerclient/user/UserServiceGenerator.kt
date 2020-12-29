package piotr.michalkiewicz.mealplannerclient.user

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.api.UserAPI
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Callback

class UserServiceGenerator : AuthServiceGenerator() {

    //  TODO Change access
    lateinit var userAPI: UserAPI

    init {
        if (!::userAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            userAPI = retrofit.create(UserAPI::class.java)
        }
    }

    fun getUserAccount(callback: Callback<UserAccount>) {
        val callAsync = userAPI.getUserAccount()
        callAsync.enqueue(callback)
    }

    fun updateUserSettings(settings: UserSettings) {
        val updateUserSettingsAPI = userAPI.updateUserSettings(settings)
        updateUserSettingsAPI.subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.i("updateUserSettings", result.toString())
            }, { error ->
                Log.i("updateUserSetting error", error.toString())
            })
    }

    fun updateUserPreference(userPreference: UserPreference): Observable<UserPreference> {
        return userAPI.updateUserPreference(userPreference)
    }

    fun getUserPreferences(): UserPreference? {
        return userAPI.getUserPreference().execute().body()
    }

    fun getUserSettings(): UserSettings? {
        return userAPI.getUserSettings().execute().body()
    }
}