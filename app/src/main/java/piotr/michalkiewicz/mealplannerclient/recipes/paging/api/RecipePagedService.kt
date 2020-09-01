package piotr.michalkiewicz.mealplannerclient.recipes.paging.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RecipePagedService {
    @GET("/recipes/getPageByDiet")
    suspend fun getRecipesForDiet(@Query("dietType") dietType: String,
                                  @Query("pageNr") pageNr: Int): RecipeSearchResponse
}