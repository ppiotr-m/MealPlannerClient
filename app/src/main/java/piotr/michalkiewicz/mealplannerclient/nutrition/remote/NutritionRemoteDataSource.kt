package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.util.Log
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import retrofit2.Response

class NutritionRemoteDataSource(
    private val nutritionService: NutritionAPI
) {
    suspend fun getNutritionUiModel(date: String, age: Int): Resource<NutritionUiModel> = getResult(
        { nutritionService.getNutritionForDate(date) }
    )

    private suspend fun getResult(
        nutritionApiCall: suspend () -> Response<NutritionUiModel>
    ): Resource<NutritionUiModel> {
        try {
            val nutritionResponse = nutritionApiCall()
            if (nutritionResponse.isSuccessful) {
                val nutritionBody = nutritionResponse.body()
                if (nutritionBody != null) {
                    return Resource.success(
                        nutritionBody
                    )
                }
            }
            return error(" ${nutritionResponse.code()} ${nutritionResponse.message()}")

        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d(ConstantValues.TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}