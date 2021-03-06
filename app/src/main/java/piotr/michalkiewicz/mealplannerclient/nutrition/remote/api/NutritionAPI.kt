package piotr.michalkiewicz.mealplannerclient.nutrition.remote.api

import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatenProductWithDate
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

    @POST("/nutrition/saveMealForToday")
    suspend fun saveMealForToday(@Body eatableItem: EatableItem):
            Response<Void>

    @POST("/nutrition/setNewDailyEatenFoods")
    suspend fun setNewDailyEatenFoods(@Body dailyEatenFoods: DailyEatenFoods):
            Response<Void>

    @POST("/nutrition/saveProductForDate")
    suspend fun saveProductForDay(@Body eatenProductWithDate: EatenProductWithDate):
            Response<Void>
}