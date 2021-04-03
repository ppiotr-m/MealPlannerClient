package piotr.michalkiewicz.mealplannerclient.recipes

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI

class RecipeServiceGenerator : AuthServiceGenerator() {

    lateinit var recipeAPI: RecipeAPI

    init {
        if (!::recipeAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeAPI = retrofit.create(RecipeAPI::class.java)
        }
    }
}