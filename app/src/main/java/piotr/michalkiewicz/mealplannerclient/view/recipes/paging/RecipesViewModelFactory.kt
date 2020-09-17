package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI

class RecipesViewModelFactory(private val recipesPagedService: RecipeAPI,
                              private val onPrependDataLoadedListener: OnPrependDataLoadedListener) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipesSearchViewModel(recipesPagedService, onPrependDataLoadedListener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}