package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository

class NutirtionGeneralTabViewModel(val repository: NutritionRepository) : ViewModel() {

    val uiModel = repository.getNutritionUiModelData()

    fun getGeneralProgressBarValue(): Int {
        if (uiModel.value?.data?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.nutrient?.name == "Protein"
            && uiModel.value?.data?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.amount!! >= 0F
        ) {
            return 90
        } else if (uiModel.value?.data?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.nutrient?.name == "Protein"
            && uiModel.value?.data?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.amount!! == 0F
        ) {
            return 20
        }
        return 50
    }
}