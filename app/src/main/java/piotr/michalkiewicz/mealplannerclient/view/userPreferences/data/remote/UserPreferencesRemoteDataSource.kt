package piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import piotr.michalkiewicz.mealplannerclient.user.api.UserAPI
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource
import javax.inject.Inject

@InstallIn(ApplicationComponent::class)
@Module
class UserPreferencesRemoteDataSource @Inject constructor(
    private val userAPI: UserAPI
) : BaseDataSource {

    suspend fun getUserPreferences(): Resource<UserPreferences> =
        getResult { userAPI.getUserPreference() }

    suspend fun setUserPreferences(userPreferences: UserPreferences): Resource<UserPreferences> =
        setData(userPreferences) {
            userAPI.updateUserPreference(userPreferences)
        }
}
