package piotr.michalkiewicz.mealplannerclient.recipes.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesPagedService {
    @GET("/recipes/getByDiet")
    suspend fun getRecipesForDiet(@Query("recipeDiet") recipeDiet: String): List<MealTimeRecipe>

    @GET("/recipes/getRecipesPageByDiet")
    suspend fun getRecipesPageForDiet(@Query("dietType") recipeDiet: String,
                                      @Query("pageNr") pageNr: Int): RecipesSearchResponse

    @GET("/recipes/getRecipesPageByType")
    suspend fun getRecipesPageForType(@Query("recipeType") recipeType: String,
                                      @Query("pageNr") pageNr: Int): RecipesSearchResponse

    @GET("/recipes/getRecipesPageByTag")
    suspend fun getRecipesPageForTag(@Query("tag") tag: String,
                                      @Query("pageNr") pageNr: Int): RecipesSearchResponse
}