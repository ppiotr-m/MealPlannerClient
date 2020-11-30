package piotr.michalkiewicz.mealplannerclient.nutrition.api

import piotr.michalkiewicz.mealplannerclient.recipes.model.EatableItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

@JvmSuppressWildcards
interface NutritionAPI {
    @GET("/nutrition/getNutritionForDate")
    suspend fun getNutritionForDate(@Query("date") date: Date): List<EatableItem>

    @POST("/user/saveNutritionForDate")
    suspend fun saveNutritionForDate(@Body nutritionData: List<EatableItem>)
}
