package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatenProductWithDate
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionRemoteDataSource
import piotr.michalkiewicz.mealplannerclient.utils.performGetOperation
import retrofit2.Response

//  TODO Move properties to constructor and add HILT
class NutritionRepository {

    private val nutritionService = NutritionServiceGenerator().nutritionAPI

    val nutritionRemoteDataSource = NutritionRemoteDataSource(
        nutritionService
    )

    fun getNutritionUiModelDataResource(date: String) =
        performGetOperation(
            networkCall = { nutritionRemoteDataSource.getNutritionUiModel(date) }
        )

    suspend fun saveProduct(eatenProductWithDate: EatenProductWithDate): Response<Void> {
        return nutritionService.saveProductForDay(eatenProductWithDate)
    }
}
