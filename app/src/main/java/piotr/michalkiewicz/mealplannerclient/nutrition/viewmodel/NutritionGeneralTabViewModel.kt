package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.utils.Resource

class NutritionGeneralTabViewModel(
    val repository: NutritionRepository
) : ViewModel() {

    val uiModel = repository.getNutritionUiModelData("2021-01-17")
}