package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserSettings : Serializable { //toDo re write it in correct way

    //If change values change them to on backend user package Constant class

    private val BASIC_PORTION_PREFERENCE = 4
    private val BASIC_COOKING_TIME_PREFERENCE = 60
    private val BASIC_MEALS_PER_PLAN_PREFERENCE = 4

    var recipeTypes: List<String> = emptyList()
    var nutritionProfileSettings: NutritionProfileSettings = NutritionProfileSettings()
    var allergies: List<String> = emptyList()
    var diets: String = ""
    var unlikeIngredients: List<String> = emptyList()
    var eatenRecipes: List<String> = emptyList()
    var cuisine: List<String> = emptyList()
    var language: String = ""
    var portionPreferences: Int = BASIC_PORTION_PREFERENCE
    var cookingTimePreference: Int = BASIC_COOKING_TIME_PREFERENCE
    var mealsPerMealPlanPreference: Int = BASIC_MEALS_PER_PLAN_PREFERENCE
    var customizationDone: Boolean = false

    override fun toString(): String {
        return "UserSettings(recipeTypesSetting=$recipeTypes, nutritionProfileSettings=$nutritionProfileSettings, allergies=$allergies, diets=$diets, unlikeIngredients=$unlikeIngredients, eatenRecipes=$eatenRecipes, language=$language, portionPreferences=$portionPreferences, cookingTimePreference=$cookingTimePreference, mealsPerMealPlanPreference=$mealsPerMealPlanPreference)"
    }

    companion object {
        private val userSettings = UserSettings()

        fun getBaseCookingValues(): Map<String, Int> {
            return mapOf(UserSettings::portionPreferences.name to userSettings.portionPreferences,
                    UserSettings::cookingTimePreference.name to userSettings.cookingTimePreference,
                    UserSettings::mealsPerMealPlanPreference.name to userSettings.mealsPerMealPlanPreference)
        }
    }
}