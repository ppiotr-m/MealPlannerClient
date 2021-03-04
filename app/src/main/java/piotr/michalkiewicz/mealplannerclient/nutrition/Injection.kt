package piotr.michalkiewicz.mealplannerclient.nutrition

import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.repository.NutritionRepository
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionViewModelFactory
import piotr.michalkiewicz.mealplannerclient.products.repository.ProductsRepository

object Injection {
    fun provideScreenViewModelFactory(): ViewModelProvider.Factory {
        return NutritionViewModelFactory(
            NutritionRepository(),
            ProductsRepository()
        )
    }

    fun provideGeneralViewModelFactory(): ViewModelProvider.Factory {
        return NutritionViewModelFactory(
            NutritionRepository(),
            ProductsRepository()
        )
    }
}