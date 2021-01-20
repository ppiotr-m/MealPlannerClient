package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutritionGeneralTabViewModel(
    val repository: NutritionRepository
) : ViewModel() {

    //  TODO Implement day selection
    val uiModel = repository.getNutritionUiModelDataResource("2021-01-17")
}