package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserSettings : Serializable {
    var recipeTypesSetting: String = ""
    var nutritionProfileSettings: NutritionProfileSettings = NutritionProfileSettings()
    var allergies: List<String> = emptyList()
    var diets: String = ""
    var unlikeIngredients: List<String> = emptyList()
    var eatenRecipes: List<String> = emptyList()
    var language: String = ""
    var portionPreferences: Int = 0
    var cookingTimePreference: Int = 0
    var mealsPerMealPlanPreference: Int = 0

    override fun toString(): String {
        return "UserSettings(recipeTypesSetting=$recipeTypesSetting, nutritionProfileSettings=$nutritionProfileSettings, allergies=$allergies, diets=$diets, unlikeIngredients=$unlikeIngredients, eatenRecipes=$eatenRecipes, language=$language, portionPreferences=$portionPreferences, cookingTimePreference=$cookingTimePreference, mealsPerMealPlanPreference=$mealsPerMealPlanPreference)"
    }
}