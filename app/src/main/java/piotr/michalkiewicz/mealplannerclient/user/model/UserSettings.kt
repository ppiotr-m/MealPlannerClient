package piotr.michalkiewicz.mealplannerclient.user.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserSettings: Serializable {
 //   var recipeTypesSetting: RecipeTypesSetting? = null
   var nutritionProfileSettings: NutritionProfileSettings? = null
   var allergies: List<String>? = null
   var diet: String? = null
   var unlikeIngredients: List<String>? = null
   var eatenRecipes: List<String>? = null
   var language: String? = null
   var portionPreferences: Int? = 4
   var cookingTimePreference: Int? = 60
   var mealsPerMealPlanPreference: Int? = 5
   var sex: String? = "Male"
}