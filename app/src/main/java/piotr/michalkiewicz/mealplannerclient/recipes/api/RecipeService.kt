package piotr.michalkiewicz.mealplannerclient.recipes.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {

    @GET("/recipes/getById")
    fun getRecipeForId(@Query("id") recipeId: String): Call<MealTimeRecipe>

    @GET("/recipes/getByDiet")
    fun getRecipeForDiet(@Query("recipeDiet") dietType: String): Call<List<MealTimeRecipe>>

    @GET("/recipes/getByType")
    fun getRecipeForType(@Query("recipeType") recipeType: String): Call<List<MealTimeRecipe>>

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