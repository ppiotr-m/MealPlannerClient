package piotr.michalkiewicz.mealplannerclient.nutrition

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionViewModelFactory
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesViewModelFactory

object Injection {
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return NutritionViewModelFactory(
            NutritionServiceGenerator().nutritionAPI
        )
    }
}