package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.recipes.database.RecipesDatabase
import piotr.michalkiewicz.mealplannerclient.recipes.remote.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeSharedViewModel

class RecipesViewModelFactory(
    private val recipesService: RecipeAPI,
    private val recipesDatabase: RecipesDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipesSearchViewModel(recipesService, recipesDatabase) as T
        } else if (modelClass.isAssignableFrom(RecipeSharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeSharedViewModel(recipesService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}