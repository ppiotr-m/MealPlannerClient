package piotr.michalkiewicz.mealplannerclient.recipes.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesPagedService {
    @GET("/recipes/getByDiet")
    suspend fun getRecipesForDiet(@Query("recipeDiet") recipeDiet: String): List<MealTimeRecipe>

    @GET("/recipes/getPageByDiet")
    suspend fun getRecipesPageForDiet(@Query("dietType") recipeDiet: String,
                                      @Query("pageNr") pageNr: Int): List<MealTimeRecipe>
}