package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG

//  TODO Make option for saving for specified date, also will need to make endpoint that doesn't take date parameter and uses now()
class NutritionDataUpdater {
    val nutritionServiceGenerator = NutritionServiceGenerator()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveMealToTodayNutrition(eatableItem: EatableItem) {
        nutritionServiceGenerator.nutritionAPI.addMealForToday(eatableItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveMealToTodayNutrition(recipe: MealTimeRecipe) {

        val mappedValues = HashMap<String, FoodNutrient>()

        Log.d(TAG, "Is summary null: " + (recipe.foodNutrientsSummary == null))
        Log.d(TAG, "Summary size: " + recipe.foodNutrientsSummary?.size)

//        val a = recipe.foodNutrientsSummary!!.get(0)

        Log.d(
            TAG,
            "Before loop, foodNutrientsSummary.toString(): " + recipe.foodNutrientsSummary!!.toString()
        )

        Log.d(
            TAG,
            "Adding recipe of name: " + recipe.name
        )

        Log.d(
            TAG,
            "Before loop, [0] toString(): " + (recipe.foodNutrientsSummary.get(0).toString())
        )

        Log.d(
            TAG,
            "Before loop, [0] element.unitName" + recipe.foodNutrientsSummary.get(0).unitName
        )

        Log.d(
            TAG,
            "Before loop, [0] element.nutrient null: " + (recipe.foodNutrientsSummary.get(0).nutrient == null)
        )


        recipe.foodNutrientsSummary.forEach {
            Log.d(TAG, "Inside loop, name: " + it.nutrient.name + " it class: " + it.javaClass)
            mappedValues.put(it.nutrient.name, it)
        }

        val item = EatableItem(
            recipe.name,
            mappedValues,
            "1",
            "portion"
        )
        nutritionServiceGenerator.nutritionAPI.addMealForToday(item)
    }
}