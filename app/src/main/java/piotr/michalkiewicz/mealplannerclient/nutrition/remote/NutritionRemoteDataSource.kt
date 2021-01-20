package piotr.michalkiewicz.mealplannerclient.nutrition.remote

import android.util.Log
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.Resource

class NutritionRemoteDataSource(
    private val nutritionService: NutritionAPI
) : BaseRemoteDataSource() {

    suspend fun getNutritionUiModel(date: String) =
        getResult { nutritionService.getNutritionForDate(date) }

    private fun <T> error(message: String): Resource<T> {
        Log.d(ConstantValues.TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}