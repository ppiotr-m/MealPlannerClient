package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.storage.NutritionSharedPrefsDao
import java.time.LocalDate

class NutritionRepository {
    val nutritionSharedPrefsAccessor = NutritionSharedPrefsDao()
    val nutritionAPI: NutritionAPI by lazy {
        NutritionServiceGenerator().nutritionAPI
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNutritionUiModelData(): NutritionUiModel {
        val nutrtionUiModelData = nutritionSharedPrefsAccessor.getDataFromSharedPrefs()
        if (nutrtionUiModelData != null) {
            if (nutrtionUiModelData.nutritionDailyData.date == LocalDate.now().toString()) {
                return nutrtionUiModelData
            } else {
                val dataFromRemote = getDataFromRemoteOrMakeInstance()

                nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
                return nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
            }
        } else {
            val dataFromRemote = getDataFromRemoteOrMakeInstance()

            nutritionSharedPrefsAccessor.saveDataToSharedPrefs(dataFromRemote)
            return nutritionSharedPrefsAccessor.getDataFromSharedPrefs()!!
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getDataFromRemoteOrMakeInstance(): NutritionUiModel {
        return nutritionAPI.getNutritionForDate(LocalDate.now().toString())
            ?: NutritionUiModel.getEmptyInstance()
    }
}
