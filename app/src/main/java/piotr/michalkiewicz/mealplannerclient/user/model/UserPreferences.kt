package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class UserPreferences(
    val recipeTypes: List<String>,
    val allergies: List<String>,
    val diet: String,
    val unlikeIngredients: List<String>,
    val cuisine: List<String>,
    val portionPreferences: Int,
    val cookingTimePreference: Int,
    val mealsPerMealPlanPreference: Int
) : Serializable
