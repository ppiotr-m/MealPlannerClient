package piotr.michalkiewicz.mealplannerclient.nutrition.remote.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionDailyData
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

    @POST("/nutrition/saveFoodsForDate")
    suspend fun saveFoodsForDate(@Body dailyEatenFoods: DailyEatenFoods):
            Response<NutritionDailyData>
}
