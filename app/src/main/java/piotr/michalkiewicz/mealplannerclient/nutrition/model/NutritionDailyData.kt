package piotr.michalkiewicz.mealplannerclient.nutrition.model

import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient

class NutritionDailyData {

    constructor(date: String, eatenFoods: List<EatableItem>) : this(date, eatenFoods) {

    }

    lateinit var dailyNutritionSummary: List<FoodNutrient>

    init {
        dailyNutritionSummary = sumFoodNutrients(eatenFoods)
    }

    private fun sumFoodNutrients(eatenFoods: List<EatableItem>): List<FoodNutrient> {
        val nutrientsWithNamesMap = mutableMapOf<String, FoodNutrient>()

        eatenFoods.forEach {
            it.foodNutrientsSummary.forEach {
                if (nutrientsWithNamesMap.containsKey(it.nutrient.name)) {
                    val currentValue = nutrientsWithNamesMap.get(it.nutrient.name)
                    nutrientsWithNamesMap.replace(it.nutrient.name, currentValue + it.amount)
                } else {
                    nutrientsWithNamesMap.put(it.nutrient.name, it)
                }
            }
        }
        return nutrientsWithNamesMap.values
    }
}
