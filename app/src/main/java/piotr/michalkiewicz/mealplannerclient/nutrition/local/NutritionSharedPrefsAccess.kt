package piotr.michalkiewicz.mealplannerclient.nutrition.local

import android.util.Log
import com.google.gson.Gson
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class NutritionSharedPrefsAccess {
    fun saveUiModelToSharedPrefs(uiModel: NutritionUiModel) {
        val objectInJsonString = Gson().toJson(uiModel)
        with(MainActivity.MY_PREFERENCES.edit()) {
            putString(NutritionScreenViewModel.NUTRITION_DATA, objectInJsonString.toString())
            apply()
        }
    }

    fun getUiModelFromSharedPrefs(): NutritionUiModel? {
        val storedNutritionUiData =
            MainActivity.MY_PREFERENCES.getString(NutritionScreenViewModel.NUTRITION_DATA, null)

        // TODO After removing log, return insted of crrating instance
        val uiModel = Gson().fromJson(
            storedNutritionUiData,
            NutritionUiModel::class.java
        )
        Log.d(TAG, "NutirtionSharedPrefsAccess::uiModel null: " + (uiModel == null))

        return uiModel
    }
}