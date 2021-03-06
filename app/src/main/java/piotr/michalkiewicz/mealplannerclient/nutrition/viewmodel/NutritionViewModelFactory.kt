package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.utils.DateRelatedService
import piotr.michalkiewicz.mealplannerclient.products.repository.ProductsRepository
import piotr.michalkiewicz.mealplannerclient.products.usda.UsdaServiceGenerator

class NutritionViewModelFactory(
    private val nutritionRepository: NutritionRepository,
    private val productsRepository: ProductsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionGeneralTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionGeneralTabViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionVitaminsTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionVitaminsTabViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionMineralsTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionMineralsTabViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionSharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionSharedViewModel(
                nutritionRepository,
                productsRepository,
                UsdaServiceGenerator().usdaAPI
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    fun <T : ViewModel?> create(
        modelClass: Class<T>,
        dateRelatedServices: List<DateRelatedService>
    ): T {
        if (modelClass.isAssignableFrom(NutritionScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionScreenViewModel(nutritionRepository, dateRelatedServices) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}