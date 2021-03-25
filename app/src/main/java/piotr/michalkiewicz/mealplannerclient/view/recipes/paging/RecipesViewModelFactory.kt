package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.recipes.api.RecipeAPI
import piotr.michalkiewicz.mealplannerclient.utils.MealTimeDatabase

class RecipesViewModelFactory(
    private val recipesPagedService: RecipeAPI,
    private val mealTImeDatabase: MealTimeDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CookbookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CookbookViewModel(recipesPagedService, mealTImeDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}