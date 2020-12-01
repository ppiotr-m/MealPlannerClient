package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel

class NutritionScreenViewModel(private val nutritionAPI: NutritionAPI) : ViewModel() {
    val uiModel: MutableLiveData<NutritionUiModel> by lazy {
        MutableLiveData<NutritionUiModel>()
    }
}