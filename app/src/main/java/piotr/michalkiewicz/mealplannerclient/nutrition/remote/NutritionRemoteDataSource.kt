package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.util.Log
import piotr.michalkiewicz.mealplannerclient.nutrition.local.NutritionDao
import piotr.michalkiewicz.mealplannerclient.nutrition.model.AgeNutrientRecommendations
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionDailyData
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.user.api.UserAPI
import piotr.michalkiewicz.mealplannerclient.user.model.NutritionProfileSettings
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import retrofit2.Response

class NutritionRemoteDataSource(
    private val nutritionService: NutritionAPI,
    private val recommendedIntakeDao: NutritionDao,
    private val userService: UserAPI
) {
    suspend fun getNutritionUiModel(date: String, age: Int): Resource<NutritionUiModel> = getResult(
        { nutritionService.getNutritionForDate(date) },
        { recommendedIntakeDao.selectRecommendationsForAge(age) },
        { userService.getUserNutritionProfileSettings() }
    )

    private suspend fun getResult(
        nutritionApiCall: suspend () -> Response<NutritionDailyData>,
        recommendedQuery: suspend () -> List<AgeNutrientRecommendations>,
        userApiCall: suspend () -> Response<NutritionProfileSettings>
    ): Resource<NutritionUiModel> {
        try {
            val nutritionResponse = nutritionApiCall()
            val userResponse = userApiCall()
            if (nutritionResponse.isSuccessful && userResponse.isSuccessful) {
                val nutritionBody = nutritionResponse.body()
                val userBody = userResponse.body()
                val ageNutrientRecommendations = recommendedQuery()
                if (nutritionBody != null && userBody != null) {
                    return Resource.success(
                        NutritionUiModel(
                            nutritionBody,
                            userBody,
                            ageNutrientRecommendations
                        )
                    )
                }
            }
            if (!nutritionResponse.isSuccessful) {
                return error(" ${nutritionResponse.code()} ${nutritionResponse.message()}")
            } else {
                return error(" ${userResponse.code()} ${userResponse.message()}")
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d(ConstantValues.TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}