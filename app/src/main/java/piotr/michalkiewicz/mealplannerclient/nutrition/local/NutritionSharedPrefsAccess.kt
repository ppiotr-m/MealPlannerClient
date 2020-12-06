package piotr.michalkiewicz.mealplannerclient.nutrition.local

import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class NutritionSharedPrefsAccess {
    fun saveDataToSharedPrefs(uiModel: NutritionUiModel) {
        val objectInJsonString = Gson().toJson(uiModel)
        MainActivity.MY_PREFERENCES.edit()
            .putString(NutritionScreenViewModel.NUTRITION_DATA, objectInJsonString)
    }

    fun getDataFromSharedPrefs(): NutritionUiModel? {
        val storedNutritionUiData =
            MainActivity.MY_PREFERENCES.getString(NutritionScreenViewModel.NUTRITION_DATA, null)
                ?: return null

        return Gson().fromJson(storedNutritionUiData, NutritionUiModel::class.java)
    }
}