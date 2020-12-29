package piotr.michalkiewicz.mealplannerclient.recipes

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import piotr.michalkiewicz.mealplannerclient.utils.MealTimeDatabase
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesViewModelFactory

object Injection {

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return RecipesViewModelFactory(
            RecipeServiceGenerator().recipeAPI,
            MealTimeDatabase.getInstance(context)
        )
    }
}