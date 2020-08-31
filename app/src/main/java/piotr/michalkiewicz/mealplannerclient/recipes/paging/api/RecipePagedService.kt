package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.http.POST

interface RecipePagedService {
    @POST("/recipes/getByDiet")
    suspend fun getRecipesForDiet(dietType: String): RecipeSearchResponse
}