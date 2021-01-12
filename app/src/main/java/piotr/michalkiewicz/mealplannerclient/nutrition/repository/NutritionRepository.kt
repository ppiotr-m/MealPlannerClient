package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionSharedPrefsAccess
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionRemoteDataSource
import piotr.michalkiewicz.mealplannerclient.utils.performGetOperation

//  TODO Move properties to constructor and add HILT
class NutritionRepository {
    val nutritionSharedPrefsAccessor = NutritionSharedPrefsAccess()

    // TODO add HILT or implement it but think again about whole idea with combining local and remote sources here
    val nutritionRemoteDataSource = NutritionRemoteDataSource(
        NutritionServiceGenerator().nutritionAPI
    )

    fun getNutritionUiModelData(date: String, age: Int) = performGetOperation(
        getFromLocalStorage = { NutritionLiveData.getInstance(nutritionSharedPrefsAccessor) },
        networkCall = { nutritionRemoteDataSource.getNutritionUiModel(date, age) },
        saveCallResult = { nutritionSharedPrefsAccessor.saveUiModelToSharedPrefs(it) }
    )

    /*
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNutritionUiModelData(date: String?): NutritionUiModel {
        // todo implement date arg processing
        val nutritionUiModelData = nutritionSharedPrefsAccessor.getDataFromSharedPrefs()
        return if (nutritionUiModelData != null) {
            if (nutritionUiModelData.nutritionDailyData.date == LocalDate.now().toString()) {
                nutritionUiModelData
            } else {
                val dataFromRemote = getDataFromRemoteOrMakeInstance()

                nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
                nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
            }
        } else {
            val dataFromRemote = getDataFromRemoteOrMakeInstance()

            nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
            nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getDataFromRemoteOrMakeInstance(): NutritionUiModel {
        return nutritionAPI.getNutritionForDate(LocalDate.now().toString())
            ?: NutritionUiModel.getEmptyInstance()
    }

     */
}
