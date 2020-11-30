package piotr.michalkiewicz.mealplannerclient.nutrition

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI

class NutritionServiceGenerator : AuthServiceGenerator() {

    lateinit var nutritionAPI: NutritionAPI

    init {
        if (!::nutritionAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            nutritionAPI = retrofit.create(NutritionAPI::class.java)
        }
    }
}

