package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutritionMineralsTabViewModel(repository: NutritionRepository): ViewModel() {
    val uiModel = repository.getNutritionUiModelData("2021-01-17")
}