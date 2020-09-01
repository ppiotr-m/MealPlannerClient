package piotr.michalkiewicz.mealplannerclient.recipes.paging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.recipes.paging.api.RecipesPagedServiceGenerator

class RecipesViewModelFactory(private val recipesPagedServiceGenerator: RecipesPagedServiceGenerator) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipesSearchViewModel(recipesPagedServiceGenerator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}