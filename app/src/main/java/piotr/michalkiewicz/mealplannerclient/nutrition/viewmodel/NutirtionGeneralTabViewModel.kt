package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutirtionGeneralTabViewModel(
    val repository: NutritionRepository,
    date: String,
    age: Int
) : ViewModel() {

    val uiModel = repository.getNutritionUiModelData(date, age)
}