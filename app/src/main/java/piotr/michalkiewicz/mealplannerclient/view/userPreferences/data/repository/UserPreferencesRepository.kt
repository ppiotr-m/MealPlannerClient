package piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.repository

import androidx.lifecycle.LiveData
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import piotr.michalkiewicz.mealplannerclient.user.UserServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserPreferences
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.local.UserPreferencesLocalStorage
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote.RecipeServiceValuesDownloader
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote.UserPreferencesRemoteDataSource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.Resource
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.performGetOperation
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.performGetOperationNoDB
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils.performSetOperation
import javax.inject.Inject

@InstallIn(ApplicationComponent::class)
@Module
class UserPreferencesRepository @Inject
constructor(
    private val userPreferencesRemoteDataSource: UserPreferencesRemoteDataSource,
    private val localStorage: UserPreferencesLocalStorage,
    private val recipeServiceValuesDownloader: RecipeServiceValuesDownloader,
    private val userServiceGenerator: UserServiceGenerator
) {
    fun getUserPreferences(): LiveData<Resource<UserPreferences?>> = performGetOperation(
        databaseQuery = { localStorage.getUserPreferences() },
        networkCall = { userPreferencesRemoteDataSource.getUserPreferences() },
        saveCallResult = { localStorage.setUserPreferences(it) }
    )

    fun setUserPreferences(userPreferences: UserPreferences): LiveData<Resource<UserPreferences?>> =
        performSetOperation(
            userPreferences,
            databaseQuery = { localStorage.getUserPreferences() },
            networkCall = { userPreferencesRemoteDataSource.setUserPreferences(userPreferences) },
            saveCallResult = { localStorage.setUserPreferences(it) }
        )

    fun getAllProductNames(): LiveData<Resource<List<String>?>> = performGetOperationNoDB(
        networkCall = { recipeServiceValuesDownloader.getAllProductsNames() }
    )

    fun getAllAllergiesNames(): LiveData<Resource<List<String>?>> = performGetOperationNoDB(
        networkCall = { recipeServiceValuesDownloader.getAllAllergiesNames() }
    )

    fun getAllDietsNames(): LiveData<Resource<List<String>?>> = performGetOperationNoDB(
        networkCall = { recipeServiceValuesDownloader.getAllDietsNames() }
    )

    fun getAllCuisinesNames(): LiveData<Resource<List<String>?>> = performGetOperationNoDB(
        networkCall = { recipeServiceValuesDownloader.getAllCuisinesNames() }
    )

    fun getAllRecipeTypeNames(): LiveData<Resource<List<String>?>> = performGetOperationNoDB(
        networkCall = { recipeServiceValuesDownloader.getAllRecipeTypesNames() }
    )

    suspend fun getUserSettings(): UserSettings? {
        return userServiceGenerator.getUserSettings()

    }

    suspend fun updateUserSettings(userSettings: UserSettings) {
        userServiceGenerator.updateUserSettings(userSettings)
    }
}
