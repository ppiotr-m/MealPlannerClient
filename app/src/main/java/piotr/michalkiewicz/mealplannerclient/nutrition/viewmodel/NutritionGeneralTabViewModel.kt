package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutritionGeneralTabViewModel(
    val repository: NutritionRepository
) : ViewModel() {

    val date = getNutritionDiaryDate()
    val age = getUserAge()
    val uiModel = repository.getNutritionUiModelData(date, age)

    private fun getUserAge(): Int {
        return 30
    }

    private fun getNutritionDiaryDate(): String {
        return "30"
    }

}