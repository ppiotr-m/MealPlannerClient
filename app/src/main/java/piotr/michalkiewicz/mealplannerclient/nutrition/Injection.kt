package piotr.michalkiewicz.mealplannerclient.nutrition

import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionViewModelFactory

object Injection {
    fun provideScreenViewModelFactory(): ViewModelProvider.Factory {
        return NutritionViewModelFactory(
            NutritionServiceGenerator().nutritionAPI
        )
    }

    fun provideGeneralViewModelFactory(): ViewModelProvider.Factory {
        return NutritionViewModelFactory(
            NutritionServiceGenerator().nutritionAPI
        )
    }
}