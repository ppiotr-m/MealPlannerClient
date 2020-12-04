package piotr.michalkiewicz.mealplannerclient.nutrition.api

import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionDailyData
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@JvmSuppressWildcards
interface NutritionAPI {
    @GET("/nutrition/getNutritionForDate")
    suspend fun getNutritionForDate(@Query("date") date: String): NutritionUiModel? // TODO Should get only NutirtionDailyData, NutritionProfileSettings should come from user repo

    @POST("/user/saveNutritionDailyData")
    suspend fun saveNutritionForDate(@Body nutritionResponse: NutritionDailyData)
}
