package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.http.POST

interface RecipeServicePagedService {
    @POST("/recipes/getByDiet")
    suspend fun getRecipeForDiet(): RecipeSearchResponse
}