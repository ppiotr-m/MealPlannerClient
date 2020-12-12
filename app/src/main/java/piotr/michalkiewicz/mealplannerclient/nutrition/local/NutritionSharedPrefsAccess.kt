package piotr.michalkiewicz.mealplannerclient.nutrition.local

import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.nutrition.model.FoodNutrientRecommendedIntake
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity
import java.util.*

class NutritionSharedPrefsAccess {
    fun saveUiModelToSharedPrefs(uiModel: NutritionUiModel) {
        val objectInJsonString = Gson().toJson(uiModel)
        MainActivity.MY_PREFERENCES.edit()
            .putString(NutritionScreenViewModel.NUTRITION_DATA, objectInJsonString)
    }

    fun getUiModelFromSharedPrefs(): NutritionUiModel? {
        val storedNutritionUiData =
            MainActivity.MY_PREFERENCES.getString(NutritionScreenViewModel.NUTRITION_DATA, null)
                ?: return null

        return Gson().fromJson(storedNutritionUiData, NutritionUiModel::class.java)
    }

    fun saveRecommendationsToSharedPrefs(recommendedIntake: List<FoodNutrientRecommendedIntake>) {
        val objectInJsonString = Gson().toJson(recommendedIntake)
        MainActivity.MY_PREFERENCES.edit()
            .putString(NutritionScreenViewModel.NUTRITION_RECOMMENDATIONS, objectInJsonString)
    }

    fun getRecommendationsFromSharedPrefs(): List<FoodNutrientRecommendedIntake>? {
        val storedNutritionUiData: String =
            MainActivity.MY_PREFERENCES.getString(
                NutritionScreenViewModel.NUTRITION_RECOMMENDATIONS,
                null
            )
                ?: return null

        return Gson().fromJson(
            storedNutritionUiData,
            LinkedList<FoodNutrientRecommendedIntake>().javaClass
        )
    }
}