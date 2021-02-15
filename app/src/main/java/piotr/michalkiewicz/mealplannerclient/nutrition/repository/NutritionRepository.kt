package piotr.michalkiewicz.mealplannerclient.nutrition.repository

import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionRemoteDataSource
import piotr.michalkiewicz.mealplannerclient.utils.performGetOperation

//  TODO Move properties to constructor and add HILT
class NutritionRepository {

    val nutritionRemoteDataSource = NutritionRemoteDataSource(
        NutritionServiceGenerator().nutritionAPI
    )

    fun getNutritionUiModelDataResource(date: String) =
        performGetOperation(
            networkCall = { nutritionRemoteDataSource.getNutritionUiModel(date) }
        )
}
