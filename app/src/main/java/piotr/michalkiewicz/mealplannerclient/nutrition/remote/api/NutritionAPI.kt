package piotr.michalkiewicz.mealplannerclient.nutrition.remote.api

import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface NutritionAPI {
    @GET("/nutrition/getUserNutritionForDate")
    suspend fun getNutritionForDate(@Query("date") date: String): Response<NutritionUiModel>

    @POST("/nutrition/addMealForDay")
    suspend fun addMealForToday(@Body eatableItem: EatableItem):
            Response<Void>
}
