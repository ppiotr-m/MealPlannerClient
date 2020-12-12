package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI

class NutritionScreenViewModel(private val nutritionAPI: NutritionAPI) : ViewModel() {

    val uiModel: MutableLiveData<NutritionUiModel> by lazy {
        MutableLiveData<NutritionUiModel>()
    }

    companion object {
        val NUTRITION_DATA = "nutrition_data"
        val NUTRITION_RECOMMENDATIONS = "nutrition_recommendations"
    }
}