package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class UserPreference(var recipeTypeSettings: List<String>,
                          var allergies: List<String>,
                          var diet: String,
                          var unlikeIngredients: List<String>,
                          var cuisine: List<String>,
                          var portionPreferences: Int,
                          var cookingTimePreference: Int,
                          var mealsPerMealPlanPreference: Int) : Serializable {

    //"Empty constructor"
    constructor() : this(emptyList(), emptyList(), "", emptyList(), emptyList(), 0, 0, 0)
}