package piotr.michalkiewicz.mealplannerclient.view.personalization.service

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.reactivex.schedulers.Schedulers
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreference

class PersonalizationService {

    private val userServiceGenerator = UserServiceGenerator()

    fun markCustomizationDone() {
        Thread {
            val userSettings = userServiceGenerator.getUserSettings()
            if (userSettings != null) {
                userSettings.customizationDone = true
                userServiceGenerator.updateUserSettings(userSettings)
            } else {
                Log.i("error", "no settings in db !!")
            }
        }.start()
    }

    fun fetchUserPreferences(): UserPreference {
        var userPreference = UserPreference()
        val getUserPreferenceThread = Thread {
            val userPreferenceResponse = userServiceGenerator.getUserPreferences()
            if (userPreferenceResponse != null) {
                Log.i("fetchUserPreferences:", userPreferenceResponse.toString())
                userPreference = userPreferenceResponse
            }
        }
        getUserPreferenceThread.start()
        getUserPreferenceThread.join()
        return userPreference
    }

    fun updateUserPreferences(userPreference: UserPreference, context: Context?) {
        val updateUserPreferenceObservable = userServiceGenerator.updateUserPreference(userPreference)
        updateUserPreferenceObservable.subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    Log.i("updateUserPreference", result.toString())
                    showMessage(context, "Your preferences have been updated")
                }, { error ->
                    Log.i("error", error.toString())
                    showMessage(context, "There was problem witch update preference please try again later")
                })
    }

    private fun showMessage(context: Context?, message: String) {
        if (context != null) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}