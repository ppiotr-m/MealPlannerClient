package piotr.michalkiewicz.mealplannerclient.recipes.model

import piotr.michalkiewicz.mealplannerclient.nutrition.model.FoodNutrient

data class RecipeIngredient(
    val amount: String,
    val unit: String,
    val originalName: String,
    val kind: String,
    override val name: String,
    override val foodNutrientsSummary: List<FoodNutrient>
) : EatableItem()