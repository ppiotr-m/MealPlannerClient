package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipePagedService {
    @POST("/recipes/getPageByDiet")
    suspend fun getRecipesForDiet(@Query("dietType") dietType: String,
                                  @Query("pageNr") pageNr: Int): RecipeSearchResponse
}