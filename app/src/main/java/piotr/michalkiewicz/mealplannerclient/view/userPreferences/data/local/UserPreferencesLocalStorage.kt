package piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_KEY
import javax.inject.Inject

@Module
@InstallIn(ApplicationComponent::class)
class UserPreferencesLocalStorage @Inject constructor(@ApplicationContext private val appContext: Context) {

    fun provideSharedPrefereneces(): SharedPreferences {
        return appContext.getSharedPreferences(
            ConstantValues.MY_PREFERENCE_NAME,
            MODE_PRIVATE
        )
    }

    fun getUserPreferences(): UserPreferences? {
        val prefs = provideSharedPrefereneces()
        val gson = Gson()
        val json: String? = prefs.getString(USER_PREFERENCES_KEY, "")
        return gson.fromJson(json, UserPreferences::class.java)
    }

    fun setUserPreferences(userPreferences: UserPreferences?) {
        val prefs = provideSharedPrefereneces()
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(userPreferences)
        prefsEditor.putString(USER_PREFERENCES_KEY, json)
        prefsEditor.apply()
    }

}