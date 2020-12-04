package piotr.michalkiewicz.mealplannerclient.recipes.model

data class FoodNutrient(
    val amount: Float,
    val unitName: String,
    val nutrient: Nutrient
)