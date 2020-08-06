package piotr.michalkiewicz.mealplannerclient.user.model

class UserSettings {
    var recipeTypesSetting: RecipeTypesSetting? = null
    var nutritionProfileSettings: NutritionProfileSettings? = null
    var allergies: Allergies? = null
    var diets: Diets? = null
    var unlikeIngredients: List<String>? = null
    var eatenRecipes: List<String>? = null
    var language: String? = null
    var portionPreferences: Int? = 4
    var cookingTimePreference: Int? = 60
    var mealsPerMealPlanPreference: Int? = 5
}