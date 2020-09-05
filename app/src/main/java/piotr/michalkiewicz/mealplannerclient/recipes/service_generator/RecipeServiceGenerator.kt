package piotr.michalkiewicz.mealplannerclient.recipes.service_generator

import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeService
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.Callback

class RecipeServiceGenerator : ServiceGenerator() {

    lateinit var recipeService: RecipeService //todo private all API methods should be here need to move from RecipePresenter

    init {
        if (!::recipeService.isInitialized) {
            val retrofit = retrofitBuilder()
            recipeService = retrofit.create(RecipeService::class.java)
        }
    }

    fun getRecipesForDiet(dietType: String, callback: Callback<List<MealTimeRecipe>>) {
        val callAsync = recipeService.getRecipeForDiet(dietType)
        callAsync.enqueue(callback)
    }

    fun getRecipeForId(id: String, callback: Callback<MealTimeRecipe>) {
        val callAsync = recipeService.getRecipeForId(id)
        callAsync.enqueue(callback)
    }

    fun getRecipesForRecipeType(recipeType: String, callback: Callback<List<MealTimeRecipe>>) {
        val callAsync = recipeService.getRecipeForType(recipeType)
        callAsync.enqueue(callback)
    }
}