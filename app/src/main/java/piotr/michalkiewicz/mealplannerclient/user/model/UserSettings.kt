package piotr.michalkiewicz.mealplannerclient.user.model

class UserSettings {
    var recipeTypesSetting: RecipeTypesSetting? = null
    var nutritionProfileSettings: NutritionProfileSettings? = null
    var allergies: Allergies? = null
    var diets: Diets? = null
    var unlikeIngredients: List<String>? = null
    var eatenRecipes: List<String>? = null
    var Language: String? = null
    var portionPreferences: Int? = null
    var cookingTimePreference: Int? = null
    var mealsPerMealPlanPreference: Int? = null
}