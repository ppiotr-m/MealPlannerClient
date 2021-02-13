package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient

@RequiresApi(Build.VERSION_CODES.N)
class NutritionDailyData(
    date: String, // TODO Need to change it to LocalDate
    eatenFoods: List<EatableItem>,
    val foodNutrientsSummary: Map<String, FoodNutrient>
) : DailyEatenFoods(date, eatenFoods)