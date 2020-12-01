package piotr.michalkiewicz.mealplannerclient.recipes.model

data class FoodNutrient(
    val number: String,
    val amount: Float,
    val unitName: String,
    val derivationCode: String,
    val nutrient: Nutrient,
    val derivationDescription: String
)