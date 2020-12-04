package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel

class NutirtionGeneralTabViewModel : ViewModel() {
    val uiModel: MutableLiveData<NutritionUiModel> by lazy {
        MutableLiveData<NutritionUiModel>()
    }

    fun getGeneralProgressBarValue(): Int {
        if (uiModel.value?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.nutrient?.name == "Protein"
            && uiModel.value?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.amount!! >= 0F
        ) {
            return 90
        } else if (uiModel.value?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.nutrient?.name == "Protein"
            && uiModel.value?.nutritionDailyData?.dailyNutritionSummary?.get(0)?.amount!! == 0F
        ) {
            return 20
        }
        return 50
    }
}