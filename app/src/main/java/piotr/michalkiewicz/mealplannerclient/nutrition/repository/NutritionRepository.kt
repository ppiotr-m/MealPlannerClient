package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionSharedPrefsDao
import piotr.michalkiewicz.mealplannerclient.utils.performGetOperation

class NutritionRepository {
    val nutritionSharedPrefsAccessor = NutritionSharedPrefsDao()
    val nutritionAPI: NutritionAPI by lazy {
        NutritionServiceGenerator().nutritionAPI
    }

    fun getNutritionUiModelData() = performGetOperation(
        getFromLocalStorage = { NutritionLiveData(nutritionSharedPrefsAccessor) },
        networkCall = { nutritionAPI.getNutritionForDate() },
        saveCallResult = { nutritionSharedPrefsAccessor.saveDataToSharedPrefs(it) }
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
