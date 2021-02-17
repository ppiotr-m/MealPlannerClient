package piotr.michalkiewicz.mealplannerclient.user

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.api.UserAPI
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import retrofit2.Callback
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class UserServiceGenerator @Inject constructor() : AuthServiceGenerator() {

    private lateinit var userAPI: UserAPI

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

    suspend fun updateUserSettings(settings: UserSettings) {
        userAPI.updateUserSettings(settings)
    }

    suspend fun updateUserPreference(userPreferences: UserPreferences): UserPreferences? {
        return userAPI.updateUserPreference(userPreferences).body()
    }

    suspend fun getUserSettings(): UserSettings? {
        return userAPI.getUserSettings().body()
    }
}