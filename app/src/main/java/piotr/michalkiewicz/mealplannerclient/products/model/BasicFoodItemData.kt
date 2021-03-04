package piotr.michalkiewicz.mealplannerclient.products.model

import piotr.michalkiewicz.mealplannerclient.recipes.model.FoodNutrient

data class BasicFoodItemData(
    val mongoId: String,
    val description: String,
    val fdcId: Int,
    val foodNutrients: List<FoodNutrient>
) {
    override fun toString(): String {
        return description
    }
}