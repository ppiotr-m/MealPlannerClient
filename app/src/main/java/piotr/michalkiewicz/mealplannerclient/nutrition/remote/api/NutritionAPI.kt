package piotr.michalkiewicz.mealplannerclient.nutrition.remote.api

import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionDailyData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@JvmSuppressWildcards
interface NutritionAPI {
    @GET("/nutrition/getNutritionForDate")
    suspend fun getNutritionForDate(date: String): Response<NutritionDailyData> // TODO Should get only NutirtionDailyData, NutritionProfileSettings should come from user repo

    @POST("/user/saveNutritionDailyData")
    suspend fun saveNutritionForDate(@Body dailyEatenFoods: DailyEatenFoods):
            Response<NutritionDailyData>
}
