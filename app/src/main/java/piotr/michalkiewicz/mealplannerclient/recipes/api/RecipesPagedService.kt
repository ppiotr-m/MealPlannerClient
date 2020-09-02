package piotr.michalkiewicz.mealplannerclient.recipes.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesPagedService {
    @GET("/recipes/getByDiet")
    suspend fun getRecipesForDiet(@Query("recipeDiet") recipeDiet: String): List<piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe>
}