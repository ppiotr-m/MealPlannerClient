package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.api.NutritionAPI
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesSearchViewModel

class NutritionViewModelFactory (
    private val nutritionAPI: NutritionAPI
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionScreenViewModel(nutritionAPI) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}