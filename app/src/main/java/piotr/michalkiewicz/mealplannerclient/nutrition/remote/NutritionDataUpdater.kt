package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem

//  TODO Make option for saving for specified date, also will need to make endpoint that doesn't take date parameter and uses now()
class NutritionDataUpdater {
    val nutritionServiceGenerator = NutritionServiceGenerator()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveMealToTodayNutrition(eatableItem: EatableItem) {
        nutritionServiceGenerator.nutritionAPI.saveMealForToday(eatableItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun setNewDailyEatenFoods(dailyEatenFoods: DailyEatenFoods) {
        nutritionServiceGenerator.nutritionAPI.setNewDailyEatenFoods(dailyEatenFoods)
    }
}