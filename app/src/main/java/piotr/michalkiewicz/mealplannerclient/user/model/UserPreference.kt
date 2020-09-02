package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

//If change values change them to on backend user package Constant class

private const val BASIC_PORTION_PREFERENCE = 4
private const val BASIC_COOKING_TIME_PREFERENCE = 60
private const val BASIC_MEALS_PER_PLAN_PREFERENCE = 4

data class UserPreference(var recipeTypes: List<String>,
                          var allergies: List<String> = emptyList(),
                          var diet: String = "",
                          var unlikeIngredients: List<String>,
                          var cuisine: List<String>,
                          var portionPreferences: Int,
                          var cookingTimePreference: Int,
                          var mealsPerMealPlanPreference: Int) : Serializable {

        constructor(): this(emptyList(), emptyList(), "" , emptyList(), emptyList(), BASIC_PORTION_PREFERENCE, BASIC_COOKING_TIME_PREFERENCE, BASIC_MEALS_PER_PLAN_PREFERENCE)

    companion object {
        fun getBaseCookingValues(): Map<String, Int> {
            return mapOf(UserPreference::portionPreferences.name to BASIC_PORTION_PREFERENCE,
                    UserPreference::cookingTimePreference.name to BASIC_COOKING_TIME_PREFERENCE,
                    UserPreference::mealsPerMealPlanPreference.name to BASIC_MEALS_PER_PLAN_PREFERENCE)
        }
    }
}