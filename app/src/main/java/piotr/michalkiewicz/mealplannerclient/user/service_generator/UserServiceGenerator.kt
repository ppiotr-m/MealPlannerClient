package piotr.michalkiewicz.mealplannerclient.user.service_generator

import android.util.Log
import io.reactivex.schedulers.Schedulers
import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Callback

class UserServiceGenerator : ServiceGenerator() {

    private lateinit var userService: UserService

    init {
        if (!::userService.isInitialized) {
            val retrofit = retrofitBuilder()
            userService = retrofit.create(UserService::class.java)
        }
    }

    fun getUserAccount(callback: Callback<UserAccount>) {  //toDo delete ?
        val callAsync = userService.getUserAccount()
        callAsync.enqueue(callback)
    }

    fun signUp(userAccount: UserAccount, callback: Callback<UserAccount>) {
        val callAsync = userService.signUp(userAccount)
        callAsync.enqueue(callback)
    }

    fun saveUserAccountData(data: UserAccount, callback: Callback<UserAccount>) {  //toDo delete ?
        val callAsync = userService.editAccountSettings(data)
        callAsync.enqueue(callback)
    }

    fun updateUserSettings(settings: UserSettings) {                                                //toDO delete ?
        val updateUserSettingsAPI = userService.updateUserSettings(settings)
        updateUserSettingsAPI.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("updateUserSettings", result.toString())
                }, { error ->
                    Log.i("error", error.toString())
                })
    }


    fun updateUserPreference(userPreference: UserPreference){
        val userCustomizationEndPoint = userService.updateUserPreference(userPreference)
        userCustomizationEndPoint.subscribeOn(Schedulers.io())
                .subscribe({result ->
                    Log.i("userPreferenceCustomiz", result.toString())
                }, { error ->
                    Log.i("error", error.toString())
                })
    }
}