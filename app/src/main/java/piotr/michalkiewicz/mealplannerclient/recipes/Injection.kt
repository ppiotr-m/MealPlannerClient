package piotr.michalkiewicz.mealplannerclient.recipes

import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesViewModelFactory

object Injection {

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return RecipesViewModelFactory(RecipeServiceGenerator().recipeAPI)
    }
}