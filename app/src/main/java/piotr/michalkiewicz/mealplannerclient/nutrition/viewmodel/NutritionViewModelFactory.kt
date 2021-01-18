package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutritionViewModelFactory(
    private val nutritionRepository: NutritionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionScreenViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionGeneralTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionGeneralTabViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionVitaminsTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionVitaminsTabViewModel(nutritionRepository) as T
        } else if (modelClass.isAssignableFrom(NutritionMineralsTabViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionMineralsTabViewModel(nutritionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}