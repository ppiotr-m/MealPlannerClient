package piotr.michalkiewicz.mealplannerclient.nutrition.model

import android.os.Build
import androidx.annotation.RequiresApi
import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient

@RequiresApi(Build.VERSION_CODES.N)
class NutritionDailyData(
    val date: String, // TODO Need to change it to LocalDate
    val eatenFoods: List<EatableItem>
) {

    val dailyNutritionSummary = sumFoodNutrients(eatenFoods)

    @RequiresApi(Build.VERSION_CODES.N)
    private fun sumFoodNutrients(eatenFoods: List<EatableItem>): List<FoodNutrient> {
        val nutrientsWithNamesMap = mutableMapOf<String, FoodNutrient>()

        eatenFoods.forEach { eatableItem ->
            eatableItem.foodNutrientsSummary.forEach { foodNutrient ->
                if (nutrientsWithNamesMap.containsKey(foodNutrient.nutrient.name)) {
                    val currentValue: Float =
                        nutrientsWithNamesMap[foodNutrient.nutrient.name]?.amount ?: 0F
                    val updatedValue = currentValue.plus((foodNutrient.amount))
                    val updatedNutrient = FoodNutrient(
                        updatedValue,
                        foodNutrient.unitName,
                        foodNutrient.nutrient
                    )
                    nutrientsWithNamesMap.replace(foodNutrient.nutrient.name, updatedNutrient)
                } else {
                    nutrientsWithNamesMap[foodNutrient.nutrient.name] = foodNutrient
                }
            }
        }
        return nutrientsWithNamesMap.values.toMutableList()
    }
}
