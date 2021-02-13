package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

//  TODO Make option for saving for specified date, also will need to make endpoint that doesn't take date parameter and uses now()
class NutritionDataUpdater {
    val nutritionServiceGenerator = NutritionServiceGenerator()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveMealToTodayNutrition(eatableItem: EatableItem) {
        Log.d(TAG, eatableItem.toString())
        nutritionServiceGenerator.nutritionAPI.addMealForToday(eatableItem)
    }
}