package piotr.michalkiewicz.mealplannerclient.recipes

import piotr.michalkiewicz.mealplannerclient.auth.AuthServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.Callback

class RecipeServiceGenerator : AuthServiceGenerator() {

    lateinit var recipeAPI: RecipeAPI

    init {
        if (!::recipeAPI.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeAPI = retrofit.create(RecipeAPI::class.java)
        }
    }

    fun getRecipesForDiet(dietType: String, callback: Callback<List<MealTimeRecipe>>) {
        val callAsync = recipeAPI.getRecipeForDiet(dietType)
        callAsync.enqueue(callback)
    }

    fun getRecipeForId(id: String, callback: Callback<MealTimeRecipe>) {
        val callAsync = recipeAPI.getRecipeForId(id)
        callAsync.enqueue(callback)
    }

    fun getRecipesForRecipeType(recipeType: String, callback: Callback<List<MealTimeRecipe>>) {
        val callAsync = recipeAPI.getRecipeForType(recipeType)
        callAsync.enqueue(callback)
    }
}