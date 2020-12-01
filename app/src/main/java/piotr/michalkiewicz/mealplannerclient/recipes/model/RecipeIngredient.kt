package piotr.michalkiewicz.mealplannerclient.recipes.model

import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem

data class RecipeIngredient(
    val amount: String,
    val unit: String,
    val originalName: String,
    val kind: String,
    val name: String,
    val foodNutrientsSummary: List<FoodNutrient>
)